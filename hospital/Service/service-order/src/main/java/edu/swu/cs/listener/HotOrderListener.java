package edu.swu.cs.listener;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rabbitmq.client.Channel;
import edu.swu.cs.Constants.SystemConstants;
import edu.swu.cs.client.WareClient;
import edu.swu.cs.domain.MqVO.OrderVO;
import edu.swu.cs.domain.ResponseResult;
import edu.swu.cs.entity.OrderInfo;
import edu.swu.cs.enums.AppHttpCodeEnum;
import edu.swu.cs.mapper.OrderInfoMapper;
import edu.swu.cs.service.IOrderInfoService;
import edu.swu.cs.utils.BeanCopyUtils;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
@RabbitListener(queues = SystemConstants.HOTORDER_QUEUE)
public class HotOrderListener {

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private WareClient wareClient;
    @Autowired
    private RedisTemplate redisTemplate;
    //因为这里mq有可能会消息重复消费，所以我们这里设计为幂等接口
    @RabbitHandler
    public void listener(OrderInfo orderInfo, Message message, Channel channel) throws IOException {

        try {
            //因为这里有消息重复的原因，所以这里需要设计幂等
            String redisKey= "MQ_HotOrderStatus:"+orderInfo.getUserId()+":"+ orderInfo.getPatientId() + ":"+orderInfo.getProductId();
            //幂等的判断时间是200ms
            Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent(redisKey, redisKey, 200, TimeUnit.MILLISECONDS);
            if (Boolean.FALSE.equals(aBoolean)){
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            }
            if (orderInfoMapper.insert(orderInfo)!=1){
                throw new RuntimeException("数据库插入失败");
            }
            rabbitTemplate.convertAndSend(SystemConstants.ORDER_EXCHANGE,"hotOrder.locked",orderInfo,new CorrelationData(UUID.randomUUID().toString()));
            //业务逻辑
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            //把下面这一行代码替换上面一行，就可以模仿消息重复消费，所以就用上面的redis防重
            //channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }catch (RuntimeException e){
            System.out.println("保存高并发订单失败" + e.getMessage());
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }
    }
}

