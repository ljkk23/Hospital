package edu.swu.cs.service.impl;


import edu.swu.cs.Constants.SystemConstants;
import edu.swu.cs.domain.ResponseResult;
import edu.swu.cs.entity.OrderInfo;
import edu.swu.cs.enums.AppHttpCodeEnum;
import edu.swu.cs.service.IOrderInfoService;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.concurrent.*;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author liujian
 * @since 2022-11-10
 */
@Service
public class OrderInfoServiceImpl implements IOrderInfoService {




    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedissonClient redissonClient;

    //@Transactional 这里和普通订单不同，这里不需要加本地事务，加了本地事务反而减少了不少吞吐量，增加了300ms的执行时间
    @Override
    public ResponseResult addHotOrder(Long userID, Long patientID, Long productID,String type) {
        long startTime=System.currentTimeMillis();
        //60秒内提交的订单视为幂等
        String redisKey= "AddHotOrder:"+userID+":"+ patientID + ":"+productID;
        //因为这里是高并发的请求，所以这里幂等的判断时间是500ms
        Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent(redisKey, redisKey, 500, TimeUnit.MILLISECONDS);
        if (Boolean.FALSE.equals(aBoolean)){
            return ResponseResult.errorResult(AppHttpCodeEnum.IDEMPOTENT_ERROR);
        }

        //业务逻辑
        // 使用库存作为分布式信号量
        RSemaphore semaphore = redissonClient.getSemaphore("Order_ware:"+productID);
        // 商品可以秒杀的数量作为信号量
        boolean b = semaphore.tryAcquire();
        if (b){
            String orderID = UUID.randomUUID().toString();
            OrderInfo orderInfo=new OrderInfo(productID,userID,patientID,orderID,type);
            //发送消息给订单服务，去处理订单
            rabbitTemplate.convertAndSend(SystemConstants.ORDER_EXCHANGE,"order.hotOrder",orderInfo,new CorrelationData(UUID.randomUUID().toString()));

            //模仿库存锁定之后，本地事务的回滚
//        int a=10/0;
        }else {
            return ResponseResult.errorResult(AppHttpCodeEnum.WARE_ERROR);
        }

        long endTime=System.currentTimeMillis(); //获取结束时间

        System.out.println("程序运行时间： "+(endTime-startTime)+"ms");


        return ResponseResult.okResult();
    }
}
