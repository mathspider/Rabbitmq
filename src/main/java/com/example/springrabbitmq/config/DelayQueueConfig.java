package com.example.springrabbitmq.config;

import com.example.springrabbitmq.Constants.DelayConstant;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DelayQueueConfig {

    //声明队列
    @Bean("delayQueue")
    public Queue delayQueue() {
        return QueueBuilder.durable(DelayConstant.DELAY_QUEUE).build();
    }

    //声明交换机
    @Bean("delayExchange")
    public CustomExchange delayExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        /**
         * 自定义交换机的类型
         * 参数1：交换机名称
         * 参数2：交换机类型
         * 参数3：是否需要持久化
         * 参数4：是否自动删除
         * 参数5：其他参数
         */
        return new CustomExchange(DelayConstant.DELAY_EXCHANGE,
                DelayConstant.DELAY_EXCHANGE_TYPE,
                true, false, args);
    }

    //绑定队列和交换机
    @Bean
    public Binding queueBinding(@Qualifier("delayQueue") Queue delayQueue,
                                @Qualifier("delayExchange") CustomExchange delayExchange) {
        return BindingBuilder
                .bind(delayQueue)
                .to(delayExchange)
                .with(DelayConstant.DELAY_ROUTING_KEY)
                .noargs();
    }

}