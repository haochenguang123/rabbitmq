package com.example.rabbitmq.simpleQueue;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author hcg
 * @version 1.0
 * @date 2021/4/7 23:04
 */
@Component
@RabbitListener(queuesToDeclare = @Queue("hello")) //消费者监听
public class HelloCustomer {

    @RabbitHandler
    public void receive1(String message) {
        System.out.println("message=" + message);
    }
}
