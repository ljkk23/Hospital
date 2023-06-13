package edu.swu.cs.listener;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rabbitmq.client.Channel;
import edu.swu.cs.Constants.SystemConstants;
import edu.swu.cs.domain.MqVO.OrderVO;
import edu.swu.cs.domain.ResponseResult;
import edu.swu.cs.entity.OrderInfo;
import edu.swu.cs.enums.AppHttpCodeEnum;
import edu.swu.cs.service.IOrderInfoService;
import edu.swu.cs.utils.BeanCopyUtils;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
@RabbitListener(queues = SystemConstants.ORDER_RELEASE_STOCK_QUEUE)
public class OrderListener {
    @Autowired
    private IOrderInfoService orderInfoService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private RedisTemplate redisTemplate;
    //因为这里mq有可能会消息重复消费，所以我们这里设计为幂等接口
    @RabbitHandler
    public void listener(OrderInfo orderInfo, Message message, Channel channel) throws IOException {

        try {
            //因为这里有消息重复的原因，所以这里需要设计幂等
            String redisKey= "MQ_OrderStatus:"+orderInfo.getUserId()+":"+ orderInfo.getPatientId() + ":"+orderInfo.getProductId();
            Object orderRedis = redisTemplate.opsForValue().get(redisKey);
            if (Objects.isNull(orderRedis)){
                redisTemplate.opsForValue().set(redisKey,redisKey,60, TimeUnit.SECONDS);
            }else {
                System.out.println("order消息重复消费错误");
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                return ;
            }

            LambdaQueryWrapper<OrderInfo> lambdaQueryWrapper=new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(OrderInfo::getOrderId,orderInfo.getOrderId());
            OrderInfo order = orderInfoService.getOne(lambdaQueryWrapper);
            OrderVO orderVO = BeanCopyUtils.copyBean(order, OrderVO.class);
            //如果没有order,说明在创建order的时候，由于本地事务回滚了，所以应该回滚库存
            if (Objects.isNull(order)){
                rabbitTemplate.convertAndSend(SystemConstants.ORDER_EXCHANGE,"stock.release",orderVO,new CorrelationData(UUID.randomUUID().toString()));
            }//如果还未支付，就设置订单的状态为取消，并且解锁库存
            else if (order.getOrderStatus()==0){
                order.setOrderStatus(3);
                orderInfoService.updateById(order);
                rabbitTemplate.convertAndSend(SystemConstants.ORDER_EXCHANGE,"stock.release",orderVO,new CorrelationData(UUID.randomUUID().toString()));
            } else if (order.getOrderStatus() == 3) {
                rabbitTemplate.convertAndSend(SystemConstants.ORDER_EXCHANGE,"stock.release",orderVO,new CorrelationData(UUID.randomUUID().toString()));
            }
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            //把下面这一行代码替换上面一行，就可以模仿消息重复消费，所以就用上面的redis防重
            //channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }catch (Exception e){
            System.out.println("订单关闭异常" + e.getMessage());
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }
    }

}
