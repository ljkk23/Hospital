package edu.swu.cs.config;

import edu.swu.cs.Constants.SystemConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
    //新建库存解锁的queue
    @Bean
    public Queue ReleaseStockQueue() {
        return new Queue(SystemConstants.RELEASE_STOCK_QUEUE, true, false, false);
    }
    //库存解锁的binding
    @Bean
    public Binding ReleaseBinding() {
        return new Binding(SystemConstants.RELEASE_STOCK_QUEUE, Binding.DestinationType.QUEUE,SystemConstants.ORDER_EXCHANGE , "stock.release", null);
    }
}
