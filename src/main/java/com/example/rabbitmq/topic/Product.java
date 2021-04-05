package com.example.rabbitmq.topic;

import com.example.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 *
 * topic 模式
 * @author hcg
 * @version 1.0
 * @date 2021/4/5 12:45
 */
public class Product {

    public static void main(String[] args) throws IOException {

        //1.获取连接
        Connection connection = RabbitMQUtils.getConnection();
        //2.获取连接通道
        Channel channel = connection.createChannel();
        //3.创建交换机
        channel.exchangeDeclare("text_topic", "topic");
        //4.发送消息
        String routingKey = "topic.user.delete";
        channel.basicPublish("text_topic",routingKey,null,("topic模式下发送的消息，routingKey："+routingKey).getBytes());
        //关闭连接
        RabbitMQUtils.closeConnectionAndChanel(channel,connection);
    }
}
