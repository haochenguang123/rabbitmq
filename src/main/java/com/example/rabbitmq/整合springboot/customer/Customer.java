package com.example.rabbitmq.整合springboot.customer;

import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * TODO 整合springboot
 *
 * @author hcg
 * @version 1.0
 * @date 2021/4/7 23:04
 */
@Component
public class Customer {

    //第一种模型： hello world  简单队列
    @RabbitListener(queuesToDeclare = @Queue(value = "hello", declare = "true", autoDelete = "false"))
    //指定某方法作为消息消费的方法，例如监听某 Queue 里面的消息
    @RabbitHandler
    public void receive1(String message) {
        System.out.println("message1：" + message + "====时间戳：" + LocalDateTime.now());
    }

    //第二种模型： work queue  工作队列  （轮询消费）  --->能者多劳需要做后续配置
    @RabbitListener(queuesToDeclare = @Queue("work_queue")) //指定某方法作为消息消费的方法，例如监听某 Queue 里面的消息
    @RabbitHandler
    public void workQueue1(String message) throws InterruptedException, IOException {
//        Thread.sleep(2000);
        System.out.println("message1：" + message + "====时间戳：" + LocalDateTime.now());

    }
    //（轮询消费）
    @RabbitListener(queuesToDeclare = @Queue("work_queue")) //指定某方法作为消息消费的方法，例如监听某 Queue 里面的消息
    @RabbitHandler
    public void workQueue2(String message) throws IOException {
        System.out.println("message2：" + message + "====时间戳：" + LocalDateTime.now());
    }

    //第三种模型： publish  fanout
    @RabbitListener(bindings = {
            @QueueBinding(value = @Queue,
                    exchange = @Exchange(value = "fanout_exchange", type = "fanout"))})
    //指定某方法作为消息消费的方法，例如监听某 Queue 里面的消息
    @RabbitHandler
    public void fanout1(String message) throws IOException {
        System.out.println("message1：" + message + "====时间戳：" + LocalDateTime.now());
    }

    @RabbitListener(bindings = {
            @QueueBinding(value = @Queue,
                    exchange = @Exchange(value = "fanout_exchange", type = "fanout"))})
    //指定某方法作为消息消费的方法，例如监听某 Queue 里面的消息
    @RabbitHandler
    public void fanout2(String message) throws IOException {
        System.out.println("message2：" + message + "====时间戳：" + LocalDateTime.now());
    }


    //第四种： direct routing
    @RabbitListener(bindings = {
            @QueueBinding(value = @Queue,
                    exchange = @Exchange(value = "direct_exchange", type = "direct")
                    , key = {"direct_queue_info"})})
    @RabbitHandler
    public void direct1(String message) throws IOException {
        System.out.println("message1：" + message + "====时间戳：" + LocalDateTime.now());
    }

    @RabbitListener(bindings = {
            @QueueBinding(value = @Queue,
                    exchange = @Exchange(value = "direct_exchange", type = "direct")
                    , key = {"direct_queue_error", "direct_queue_info"})
    })
    @RabbitHandler
    public void direct2(String message) throws IOException {
        System.out.println("message2：" + message + "====时间戳：" + LocalDateTime.now());
    }


    //第四种： topic routing
    @RabbitListener(bindings = {
            @QueueBinding(value = @Queue,
                    exchange = @Exchange(value = "topic_exchange", type = "topic"),
                    key = {"topic.*"})
    })
    //指定某方法作为消息消费的方法，例如监听某 Queue 里面的消息
    @RabbitHandler
    public void topic1(String message) throws IOException {
        System.out.println("message1：" + message + "====时间戳：" + LocalDateTime.now());
    }

    @RabbitListener(bindings = {
            @QueueBinding(value = @Queue,
                    exchange = @Exchange(value = "topic_exchange", type = "topic"),
                    key = {"topic.#"})
    })
    //指定某方法作为消息消费的方法，例如监听某 Queue 里面的消息
    @RabbitHandler
    public void topic2(String message) throws IOException {
        System.out.println("message2：" + message + "====时间戳：" + LocalDateTime.now());
    }
}
