package edu.swu.cs.job;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import edu.swu.cs.client.WareClient;
import edu.swu.cs.entity.Product;
import edu.swu.cs.entity.Ware;
import edu.swu.cs.service.IProductService;
import org.redisson.api.RKeys;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.naming.SelectorContext.prefix;

@Component
public class upProductJob {
    @Autowired
    private IProductService productService;


    @Resource
    private RedissonClient redissonClient;

    @Resource
    private RedisTemplate redisTemplate;


    @Autowired
    private WareClient wareClient;
    //每两周的星期天的中午12：00将后面两周的产品上架
//    @Scheduled(cron = "0 12 8-14,22-28 * 7")
    //@Scheduled(cron = "*/5 * * * * ?")
    public void upProduct(){
        LocalDate now = LocalDate.now();
        LocalDate end=now.plusDays(14L);
        System.out.println("now:"+now+"end:"+end);
        String nowTime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String endTime = end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LambdaQueryWrapper<Product> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.between(Product::getDate,nowTime,endTime);
        List<Product> list = productService.list(lambdaQueryWrapper);
        List<Product> hotOrderList=list;
        //过滤出高并发订单
        hotOrderList.stream().filter(product -> Objects.equals(product.getType(), "1"))
                .forEach(product -> {
                    RSemaphore semaphore = redissonClient.getSemaphore("Order_ware:"+product.getId());
                    semaphore.trySetPermits(wareClient.getWareByProductId(product.getId()));
                });
        list.forEach(p-> p.setStatus("1"));
        productService.updateBatchById(list);
    }
    //每隔15分钟将实际的库存和redis的库存进行更新
    //@Scheduled(cron = "*/15 * * * *")
//    @Scheduled(cron = "0 0/2 * * * ?")
    //@Scheduled(cron = "*/30 * * * * ?")
    public void updateProductWare(){
        Set<String> keys = redisTemplate.keys("Order_ware:*");
        System.out.println(keys.toString());
        //如果为空则不同步
        if(keys==null || keys.isEmpty()){
            return;
        }
        keys.stream().forEach(s -> {
            Integer Count = (Integer)redisTemplate.opsForValue().get(s);
            System.out.println(Count);
            String[] split = s.split(":");
            Long productID=Long.valueOf(split[1]);
            System.out.println(productID);
            Boolean aBoolean = wareClient.updateHotOrderWare(productID,Count);

        });
    }


}
