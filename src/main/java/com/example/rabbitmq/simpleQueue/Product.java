package com.example.rabbitmq.simpleQueue;

import com.example.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.junit.Test;

import java.io.IOException;

/**
 * 简单队列：生产者
 *
 * @author hcg
 * @version 1.0
 *
 *       p  --------> c
 *
 */


public class Product {


    //队列名称
    private String QUEUE_NAME = "simple_queue_hello";
    private String QUEUE_NAME_DURABLE = "simple_queue_hello_durable";

    @Test
    public void customerData() throws IOException {

        //1.通过工具类获取连接
        Connection connection = RabbitMQUtils.getConnection();
        //2.获取连接通道
        Channel channel = connection.createChannel();
        //3.声明队列---->创建队列时，赋予队列的特性要一一对应（都持久化，都自动删除）
        //通道绑定对应消息队列
        //参数1:队列名称，如果队列不存在自动创建
        //参数2：用来定义队列特性是否要持久化  true 持久化队列  false 不持久化队列
        //参数3：exclusive是否独占队列    true  独占   false  不独占
        //参数4：autoDelete:是否在消费完之后自动删除队列   true  删除   false  不自动删除
        //参数5：额外附加参数
        channel.queueDeclare(QUEUE_NAME_DURABLE, true, false, false, null);
        //4.获取消息（定义消息）
        String message = "simple_queue,hello world!";
        //5.发布
        channel.basicPublish("", QUEUE_NAME_DURABLE, null, message.getBytes());
        //6.关闭连接
        RabbitMQUtils.closeConnectionAndChanel(channel, connection);
    }

}
