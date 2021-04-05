package com.example.rabbitmq.publish;

import com.example.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.junit.Test;

import java.io.IOException;

/**
 * 广播---fanout-----发布订阅 publish / subscribe
 *
 * @author hcg
 * @version 1.0
 * @date 2021/1/28 21:13
 *                   ---------  c
 *    p----  x -----
 *                   ---------  c
 *
 */
public class Product {

    //队列名称
    private static final String EXCHANG_NAME = "text_exchage_fanout";

    @Test
    public void sendMessage() throws IOException {

        //1.获取链接
        Connection connection = RabbitMQUtils.getConnection();
        //2.获取连接通道
        Channel channel = connection.createChannel();
        //3.申明交换机
        //参数1： 交换机名称
        //参数2： 交换机类型   fanout 广播
        channel.exchangeDeclare("text_exchange_fanout", "fanout");
        //通道绑定对应消息队列
        //参数1:队列名称，如果队列不存在自动创建
        //参数2：用来定义队列特性是否要持久化  true 持久化队列  false 不持久化队列
        //参数3：exclusive是否独占队列    true  独占   false  不独占
        //参数4：autoDelete:是否在消费完之后自动删除队列   true  删除   false  不自动删除
        //参数5：额外附加参数
        //todo 1. 自定义申明队列
//        channel.queueDeclare(EXCHANG_NAME, false, false, false, null);
        //todo 2.使用临时队列
//        String queue = channel.queueDeclare().getQueue();

        //4.发布消息
        //参数1：交换机名称   参数2：队列名称   参数3：传递消息额外设置   参数4：消息的具体内容
        channel.basicPublish("text_exchange_fanout", "", null, "hello rabbitmq fanout".getBytes());
        //5.通过工具类来关闭连接
        RabbitMQUtils.closeConnectionAndChanel(channel, connection);
    }
}
