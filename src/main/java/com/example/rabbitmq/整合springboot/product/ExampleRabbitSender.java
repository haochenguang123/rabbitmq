package com.example.rabbitmq.整合springboot.product;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import static com.example.rabbitmq.整合springboot.contact.CommonContact.MQ_QUEUE_AUDIT_LOG;
import static com.example.rabbitmq.整合springboot.contact.CommonContact.MQ_TOPIC_EXCHANGE;


/**
 * 消息发送者
 *
 * @author eamon.zhang
 * @date 2020-04-02 10:08 AM
 */
@Slf4j
@Service
public class ExampleRabbitSender {

    /**
     * 被发送的数据，由于需求中需要将发送失败的消息记录下来，所以这里需接受发送的数据
     */
    private Object data;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                log.info("Message Confirm Success: correlationData({}), ack({}), cause({})", correlationData, ack, cause);
            } else {
                log.info("Message Confirm Failed: correlationData({}), ack({}), cause({})", correlationData, ack, cause);
                log.error("MQ_SEND_FAILED AUDIT_LOG_DATA：{}", JSONUtil.toJsonStr(data));
            }
        });
    }

    public void convertAndSend(final Object data) throws AmqpException {
        this.convertAndSend(MQ_TOPIC_EXCHANGE, MQ_QUEUE_AUDIT_LOG, data);
    }

    public void convertAndSend(final Object data, CorrelationData correlationData) throws AmqpException {
        this.convertAndSend(MQ_TOPIC_EXCHANGE, MQ_QUEUE_AUDIT_LOG, data, correlationData);
    }

    public void convertAndSend(String exchange, String routingKey, final Object data) throws AmqpException {
        this.convertAndSend(exchange, routingKey, data, null);
    }

    /**
     * 消息发送
     *
     * @param exchange        交换机
     * @param routingKey      路由键
     * @param data            数据
     * @param correlationData 消息id
     * @throws AmqpException
     */
    public void convertAndSend(String exchange, String routingKey, final Object data,
                               CorrelationData correlationData) throws AmqpException {
        this.data = data;
        rabbitTemplate.convertAndSend(exchange, routingKey, data, correlationData);
    }

}