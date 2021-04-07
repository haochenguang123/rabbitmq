package com.example.rabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes =DemoApplication.class)
@RunWith(SpringRunner.class)
public class DemoApplicationTests {

    //注入rabbitmq
    @Autowired
    private RabbitTemplate rabbitTemplate;

    //hello world
    @Test
    public void test() {
        rabbitTemplate.convertAndSend("hello","hello world");
    }

}
