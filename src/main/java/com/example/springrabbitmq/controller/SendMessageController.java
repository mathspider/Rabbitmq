package com.example.springrabbitmq.controller;


import com.example.springrabbitmq.Constants.DelayQueueOneConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping("send")
public class SendMessageController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 队列延迟
     */
    @GetMapping("/ttl/{message}")
    public void sendMessage(@PathVariable String message) {
        log.info("当前时间：{},发送一条信息给两个 TTL 队列:{}", LocalTime.now(), message);
        rabbitTemplate.convertAndSend(DelayQueueOneConstant.NORMAL_EXCHANGE,
                DelayQueueOneConstant.NORMAL_ROUTING_A,
                "消息来自 ttl 为 10S 的队列: " + message);
        rabbitTemplate.convertAndSend(DelayQueueOneConstant.NORMAL_EXCHANGE,
                DelayQueueOneConstant.NORMAL_ROUTING_B,
                "消息来自 ttl 为 30S 的队列: " + message);
    }
    /**
     * 队列延迟优化
     *
     * @param message
     * @param ttl
     */
    @GetMapping("/ttl/{message}/{ttl}")
    public void sendMsg(@PathVariable String message, @PathVariable String ttl) {
        rabbitTemplate.convertAndSend(DelayQueueOneConstant.NORMAL_EXCHANGE,
                DelayQueueOneConstant.NORMAL_ROUTING_C, message, correlationData -> {
                    correlationData.getMessageProperties().setExpiration(ttl);
                    return correlationData;
                });
        log.info("当前时间：{},发送一条时长{}毫秒 TTL 信息给队列 C:{}",
                LocalTime.now(), ttl, message);
    }
}