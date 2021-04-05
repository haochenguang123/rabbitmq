package com.example.rabbitmq.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQUtils {


    public static final ConnectionFactory connectionFactory;

    /**
     * 静态方法 类启动加载一次， 重量级资源
     */
    static {
        connectionFactory = new ConnectionFactory();

        //设置连接rabbitmq主机
        connectionFactory.setHost("192.168.1.120");
        //设置连接rabbitmq端口
        connectionFactory.setPort(5672);
        //设置连接哪个虚拟主机
        connectionFactory.setVirtualHost("/user_1");
        //设置访问虚拟主机的用户名
        connectionFactory.setUsername("user_1");
        //设置访问虚拟主机的密码
        connectionFactory.setPassword("123456");

    }
    public static Connection getConnection(){//创建链接mq的连接工厂对象
        try{
            Connection connection = connectionFactory.newConnection();
            return connection;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 关闭通道和关闭连接的工具方法
     *
     * @param channel
     * @param connection
     */

    public static void closeConnectionAndChanel(Channel channel, Connection connection) {//创建链接mq的连接工厂对象
        try {
            if (channel != null) channel.close();
            if (connection != null) connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
