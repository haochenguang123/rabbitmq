package com.example.rabbitmq.work_queue;

import com.example.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author hcg
 * @version 1.0
 */
public class Customer1 {

    public static void main(String[] args) throws IOException {
        //1.创建链接
        Connection connection = RabbitMQUtils.getConnection();
        //2.创建消息通道
        Channel channel = connection.createChannel();

        channel.basicQos(1); //每次通道 只能消费一条消息（避免消息被全部拿到通道，宕机导致消息丢失）
        //3.创建通道上的队列
        channel.queueDeclare("work_queue", true, false, false, null);
        //4.消费消息
        //参数1：队列名称
        // 参数2： 消息自动确认true 消费者自动向rabbitmq确认消息，false 不会自动确认
        channel.basicConsume("work_queue", false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("消费者1===：" + new String(body));
                //参数1：确认队列中那个具体消息
                //参数2： 是否开启多个消息同时确认
                channel.basicAck(envelope.getDeliveryTag(),false);//手动确认

            }
        });
    }
}
