package edu.swu.cs.config;

import edu.swu.cs.Constants.SystemConstants;
import io.micrometer.core.lang.Nullable;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class myRabbitConfig {
    /**
     * 使用JSON序列化机制,进行消息转换
     *
     * @return
     */
    @Bean
    public MessageConverter messageConverter() {
        return new FastJsonMessageConverter();
    }



    /**
     * 创建交换器，参数说明：
     * String name：交换器名称
     * boolean durable：设置是否持久化，默认是 false。durable 设置为 true 表示持久化，反之是非持久化。
     * 持久化可以将交换器存盘，在服务器重启的时候不会丢失相关信息。
     * boolean autoDelete：设置是否自动删除，为 true 则设置队列为自动删除，
     * arguments,表示交换机的参数
     */

    @Bean
    public Queue HotOrderQueue() {
        return new Queue(SystemConstants.HOTORDER_QUEUE, true, false, false);
    }
    //接收订单处理的队列
    @Bean
    public Binding HotOrderBinding() {
        return new Binding(SystemConstants.HOTORDER_QUEUE, Binding.DestinationType.QUEUE,SystemConstants.ORDER_EXCHANGE , "order.hotOrder", null);
    }
    //判断高并发订单的delayQueue
    @Bean
    public Queue hotOrderDelayQueue() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", SystemConstants.ORDER_EXCHANGE);
        arguments.put("x-dead-letter-routing-key", "order.release");
        arguments.put("x-message-ttl", 60000);
        return new Queue(SystemConstants.HOTORDER_DELAY_QUEUE, true, false, false, arguments);
    }

    @Bean
    public Binding hotOrderDelayBinding() {
        return new Binding(SystemConstants.HOTORDER_DELAY_QUEUE, Binding.DestinationType.QUEUE, SystemConstants.ORDER_EXCHANGE, "hotOrder.locked", null);
    }





    //第一次监听消息时，idea会连接rabbitMQ,此时才会创建rdbbitMQ中没有的队列、交换机和绑定关系
    //如果需要修改rabbitMQ中已存在的队列交换机,需要先删除，然后再次创
}
