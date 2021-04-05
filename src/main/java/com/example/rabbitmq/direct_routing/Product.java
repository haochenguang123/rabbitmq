package com.example.rabbitmq.direct_routing;

import com.example.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * 路由模式 ---> routing /direct
 *
 * @author hcg
 * @version 1.0
 * @date 2021/4/4 11:59
 * --------
 * p ------ x --------  --------
 * --------
 */
public class Product {

    public static void main(String[] args) throws IOException {

        //1.获取连接
        Connection connection = RabbitMQUtils.getConnection();
        //2.获取连接通道
        Channel channel = connection.createChannel();
        //3.申明交换机
        channel.exchangeDeclare("text_exchange_direct", "direct");
        //4.创建队列
//        channel.queueDeclare("text_queue_direct", true, false, false, null);

        //5.发送消息
        String routingKey = "warning";
        channel.basicPublish("text_exchange_direct", routingKey, null,
                ("这是direct模板发布的基于routing key：[" + routingKey + "],发送的消息").getBytes());
        //6.关闭连接
        RabbitMQUtils.closeConnectionAndChanel(channel, connection);
    }

}
