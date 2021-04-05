package com.example.rabbitmq.direct_routing;

import com.example.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author hcg
 * @version 1.0
 * @date 2021/4/4 12:18
 */
public class Customer1 {

    public static void main(String[] args) throws IOException {

        //1.获取连接
        Connection connection = RabbitMQUtils.getConnection();
        //2.获取通道
        Channel channel = connection.createChannel();
        //3.申明交换机
        channel.exchangeDeclare("text_exchange_direct", "direct");
        //4.建立临时队列
        String queue = channel.queueDeclare().getQueue();
        //5.绑定交换机，临时队列，routingKey
        channel.queueBind(queue, "text_exchange_direct", "info");
        //6.消费消息
        channel.basicConsume(queue, false, new DefaultConsumer(channel) {

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者1："+new String(body));
            }
        });

    }
}
