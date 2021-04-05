package com.example.rabbitmq.publish;

import com.example.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * 发布--订阅-- ----》消费者1
 *
 * @author hcg
 * @version 1.0
 */
public class Customer2 {

    //队列名称
    private static final String EXCHANG_NAME = "text_exchage_fanout";

    public static void main(String[] args) throws IOException {
        //1.通过工具类获取连接
        Connection connection = RabbitMQUtils.getConnection();
        //2.获取连接中通道
        Channel channel = connection.createChannel();

        //通道绑定对应消息队列
        //参数1:队列名称，如果队列不存在自动创建
        //参数2：用来定义队列特性是否要持久化  true 持久化队列  false 不持久化队列
        //参数3：exclusive是否独占队列    true  独占   false  不独占
        //参数4：autoDelete:是否在消费完之后自动删除队列   true  删除   false  不自动删除
        //参数5：额外附加参数
//                channel.queueDeclare(EXCHANG_NAME,false,false,false,null);
        // 3.申明交换机
        channel.exchangeDeclare("text_exchange_fanout", "fanout");
        //4.创建临时队列
        String queue = channel.queueDeclare().getQueue();
        //5.绑定 临时队列
        channel.queueBind(queue, "text_exchange_fanout", "");
        //6.消费消息
        channel.basicConsume(queue, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String s = new String(body, "utf-8");
                System.out.println("消费者2：" + s);
            }
        });

    }
}
