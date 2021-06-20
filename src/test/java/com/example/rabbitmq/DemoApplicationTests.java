package com.example.rabbitmq;

import cn.hutool.core.util.IdUtil;
import com.example.rabbitmq.整合springboot.product.ExampleRabbitSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Scope;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@SpringBootTest(classes = DemoApplication.class)
@RunWith(SpringRunner.class)
public class DemoApplicationTests {

    //注入rabbitmq
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private ExampleRabbitSender exampleRabbitSender;


    //1.hello world
    @Test
    public void helloWorld() {
        rabbitTemplate.convertAndSend("hello", "hello world");
    }

    //2.work queue
    @Test
    public void workQueue() {
        for (int i = 0; i < 10; i++) {
            exampleRabbitSender.convertAndSend("hello work queue" + "消息=" + i, new CorrelationData(IdUtil.simpleUUID()));

        }
    }

    //3.publish --fanout  广播模式
    //fanout类型的交换机在发送消息的时候，RoutingKey可以不用指定，因为指定了也无效，该交换机会向绑定的所有队列中发送数据
    @Test
    public void fanout() {
        rabbitTemplate.convertAndSend("fanout_exchange", "", "hello fanout queue 消息");
    }

    //4. routing  --- direct
    @Test
    public void routing() {
        String routing = "direct_queue_error";
//        String routing = "direct_queue_info";
        rabbitTemplate.convertAndSend("direct_exchange", routing, "hello direct queue=====" + routing);
    }


    //5. topics  ---
    @Test
    public void topic() {

        String routing = "topic.info.1";
        rabbitTemplate.convertAndSend("topic_exchange", routing, "hello topic queue=====" + routing);
    }

}
