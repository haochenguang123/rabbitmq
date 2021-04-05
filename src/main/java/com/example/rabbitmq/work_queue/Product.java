package com.example.rabbitmq.work_queue;

import com.example.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.junit.Test;

import java.io.IOException;

/**todo work_queue 工作队列
 * @author hcg
 * @version 1.0
 *
 *             ---------  c
 *      p----
 *             ---------  c
 */
public class Product {

    @Test
    public void testWorkQueue() throws IOException {
        //1,获取链接
        Connection connection = RabbitMQUtils.getConnection();
        //2.创建消息通道
        Channel channel = connection.createChannel();
        //3.通过通道申明队列
        channel.queueDeclare("work_queue", true, false, false, null);
        //4. 生产消息
        for (int i = 0; i < 20000; i++) {
            channel.basicPublish("", "work_queue", null, ("第" + i + "个work_queue,hello").getBytes());
        }
        //5.关闭资源
        RabbitMQUtils.closeConnectionAndChanel(channel,connection);
    }
}
