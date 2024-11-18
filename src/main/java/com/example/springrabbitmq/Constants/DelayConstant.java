package com.example.springrabbitmq.Constants;

public class DelayConstant {
    /**
     * 延迟队列实现延迟消息
     */
    public static final String DELAY_QUEUE = "delay.queue";
    public static final String DELAY_EXCHANGE = "delay.exchange";
    public static final String DELAY_ROUTING_KEY = "delay.routingKey";
    //交换机类型为延迟交换机
    public static final String DELAY_EXCHANGE_TYPE = "x-delayed-message";
}
