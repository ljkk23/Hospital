package edu.swu.cs.listener;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rabbitmq.client.Channel;
import edu.swu.cs.Constants.SystemConstants;
import edu.swu.cs.domain.MqVO.OrderVO;
import edu.swu.cs.entity.Ware;
import edu.swu.cs.service.IWareService;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
@RabbitListener(queues = SystemConstants.RELEASE_STOCK_QUEUE)
public class WareListener {
    @Autowired
    private IWareService wareService;
    @Autowired
    private RedisTemplate redisTemplate;

    @Resource
    private RedissonClient redissonClient;

    /**  @Transactional
        因为这里mq有可能会消息重复消费，所以我们这里设计为幂等接口,
    方案一：第一次访问时，在redis中判断是否有该key，没有就加进去，有就说明多次访问了
    方案二: 设计一张防重表，给每个操作一个唯一id,然后插入数据库，和第一次一样，只不过是访问数据库罢了
     *
     * @param orderVO
     * @param message
     * @param channel
     * @throws IOException
     */
    @RabbitHandler
    public void listener(OrderVO orderVO, Message message, Channel channel) throws IOException {
        System.out.println("进入解锁解库存");
        try {
            //因为这里有消息重复的原因，所以这里需要设计幂等
            String redisKey= "MQ_Ware:"+orderVO.getUserId()+":"+ orderVO.getPatientId() + ":"+orderVO.getProductId();
            Object orderRedis = redisTemplate.opsForValue().get(redisKey);
            if (Objects.isNull(orderRedis)){
                redisTemplate.opsForValue().set(redisKey,redisKey,60, TimeUnit.SECONDS);
            }else {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                return ;
            }
            //业务逻辑
            //如果是普通的
            if (Objects.equals(orderVO.getType(), "0")){
                LambdaQueryWrapper<Ware> lambdaQueryWrapper=new LambdaQueryWrapper<>();
                lambdaQueryWrapper.eq(Ware::getProductId,orderVO.getProductId());
                Ware ware = wareService.getOne(lambdaQueryWrapper);
                //解锁库存
                ware.setLockAmount(ware.getLockAmount()-1);
                wareService.updateById(ware);
            } else if (Objects.equals(orderVO.getType(), "1")) {
                //如果是高并发
                RSemaphore semaphore = redissonClient.getSemaphore("Order_ware:"+orderVO.getProductId());
                semaphore.release();
            }

            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }catch (Exception e){
            System.out.println("库存解锁异常" + e.getMessage());
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }

    }
}