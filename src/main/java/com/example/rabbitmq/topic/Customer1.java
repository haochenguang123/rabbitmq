package com.example.rabbitmq.topic;

import com.example.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * topic 模式消费--1
 *
 * @author hcg
 * @version 1.0
 */

public class Customer1 {

    public static void main(String[] args) throws IOException {
        //1.获取连接
        Connection connection = RabbitMQUtils.getConnection();
        //2.获取通道
        Channel channel = connection.createChannel();
        //3.申明交换机
        channel.exchangeDeclare("text_topic", "topic");
        //4.创建队列
        String queue = channel.queueDeclare().getQueue();
        //5.绑定队列，交换机
        channel.queueBind(queue, "text_topic", "topic.*");
        //6.消费消息
        channel.basicConsume(queue, false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("topic 消费者1：" + new String(body));
            }
        });
    }

}
