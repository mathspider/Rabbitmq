package com.example.springrabbitmq.config;

import com.example.springrabbitmq.Constants.DelayQueueOneConstant;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class QueueConfig {

    //声明普通交换机
    @Bean("normalExchange")
    public DirectExchange normalExchange() {
        return new DirectExchange(DelayQueueOneConstant.NORMAL_EXCHANGE);
    }

    //声明死信交换机
    @Bean("deadExchange")
    public DirectExchange deadExchange() {
        return new DirectExchange(DelayQueueOneConstant.DEAD_EXCHANGE);
    }

    //声明队列A
    @Bean("queueA")
    public Queue queueA() {
        Map<String, Object> args = new HashMap<>(3);
        //声明当前队列绑定的死信交换机
        args.put("x-dead-letter-exchange", DelayQueueOneConstant.DEAD_EXCHANGE);
        //声明当前队列的死信路由 key
        args.put("x-dead-letter-routing-key", DelayQueueOneConstant.DEAD_ROUTING);
        //声明队列的 TTL
        args.put("x-message-ttl", 10000);
        return QueueBuilder.durable(DelayQueueOneConstant.NORMAL_QUEUE_A).withArguments(args).build();
    }

    //声明队列B
    @Bean("queueB")
    public Queue queueB() {
        Map<String, Object> args = new HashMap<>(3);
        //声明当前队列绑定的死信交换机
        args.put("x-dead-letter-exchange", DelayQueueOneConstant.DEAD_EXCHANGE);
        //声明当前队列的死信路由 key
        args.put("x-dead-letter-routing-key", DelayQueueOneConstant.DEAD_ROUTING);
        //声明队列的 TTL
        args.put("x-message-ttl", 30000);
        return QueueBuilder.durable(DelayQueueOneConstant.NORMAL_QUEUE_B).withArguments(args).build();
    }

    //声明死信队列
    @Bean("queueD")
    public Queue queueD() {
        return QueueBuilder.durable(DelayQueueOneConstant.DEAD_QUEUE).build();
    }

    //声明队列A绑定普通交换机
    @Bean
    public Binding queueABinding(@Qualifier("queueA") Queue queueA,
                                 @Qualifier("normalExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queueA).to(exchange).with(DelayQueueOneConstant.NORMAL_ROUTING_A);
    }

    //声明队列B绑定普通交换机
    @Bean
    public Binding queueBBinding(@Qualifier("queueB") Queue queueB,
                                 @Qualifier("normalExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queueB).to(exchange).with(DelayQueueOneConstant.NORMAL_ROUTING_B);
    }

    //声明队列D绑定死信交换机
    @Bean
    public Binding queueDBinding(@Qualifier("queueD") Queue queueD,
                                 @Qualifier("deadExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queueD).to(exchange).with(DelayQueueOneConstant.DEAD_ROUTING);
    }
    @Bean("queueC")
    public Queue queueC() {
        Map<String, Object> args = new HashMap<>(3);
        //声明当前队列绑定的死信交换机
        args.put("x-dead-letter-exchange", DelayQueueOneConstant.DEAD_EXCHANGE);
        //声明当前队列的死信路由 key
        args.put("x-dead-letter-routing-key", DelayQueueOneConstant.DEAD_ROUTING);
        return QueueBuilder.durable(DelayQueueOneConstant.NORMAL_QUEUE_C).withArguments(args).build();
    }

    //声明队列C绑定普通交换机
    @Bean
    public Binding queueCBinding(@Qualifier("queueC") Queue queueC,
                                 @Qualifier("normalExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queueC).to(exchange).with(DelayQueueOneConstant.NORMAL_ROUTING_C);
    }
}