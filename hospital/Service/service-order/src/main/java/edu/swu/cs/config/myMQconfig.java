package edu.swu.cs.config;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import io.micrometer.core.lang.Nullable;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class myMQconfig {
    @Autowired
    RabbitTemplate rabbitTemplate;
    /*
    定制RabbitTemplate
     */
    @PostConstruct //MyRabbitConfig对象创建完成以后,执行这个方法
    public void initRabbitTemplate() {
        //设置确认回调
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            /*
            correlationData当前消息的唯一关联数据(消息的唯一id)，
            ack消息是否成功收到
            cause失败原因
             */
            @Override
            public void confirm(@Nullable CorrelationData correlationData, boolean b, @Nullable String s) {
                System.out.println("confirm...CorrelationData[" + correlationData + "]==>[" + b + "]==>[" + s + "]");
            }
        });


        //设置消息抵达队列的确认回调
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            /*
            只要消息没有投递到指定队列，就触发这个失败回调
             */
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                System.out.println("Fail Message[" + message + "]==>replyCode[" + replyCode
                        + "]==>replyText[" + replyText + "]==>[exchange" + exchange + "]==>routingKey[" + routingKey + "]");
            }
        });
    }


}
