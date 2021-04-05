package com.example.rabbitmq.simpleQueue;

import com.example.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 简单队列：消费者
 *
 * @author hcg
 * @version 1.0
 * @date 2021/2/6 22:23
 */
public class Customer {

    //队列名称
    private static String QUEUE_NAME = "simple_queue_hello";

    //main方法可以使线程运行。    @Test -->杀死线程
    public static void main(String[] args) throws IOException {

        //1.获取连接
        Connection connection = RabbitMQUtils.getConnection();
        //2.获取连接通道
        Channel channel = connection.createChannel();
        //3.指定消费的队列名称
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //4.消费者
        channel.basicConsume(QUEUE_NAME, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String s = new String(body, "UTF-8");
                System.out.println("消费者：" + s + "__时间戳：" + LocalDateTime.now());
            }
        });
    }

}
