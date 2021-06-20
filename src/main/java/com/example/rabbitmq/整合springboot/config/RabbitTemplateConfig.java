package com.example.rabbitmq.整合springboot.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.SerializerMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 业务需求
 * 为了消息的可靠性投递，我们一般会启用消息发送确认和返回机制即 confirm 和 return 机制来确定消息是否发送成功。
 * <p>
 * 异常信息的出现
 * 业务逻辑中动态添加消息确认或返回确认逻辑时，可能会遇到：Only one ReturnCallback is supported by each RabbitTemplate
 * 或 Only one ConfirmCallback is supported by each RabbitTemplate 的异常信息。
 *
 * 异常分析
 * 意思就是说每个 RabbitTemplate 对象只支持一个 ConfirmCallback / ReturnCallback 发送确认回调。
 * 而默认情况下，SpringBoot中通过@Autowired 注入的 Bean 都为单例，所以我们如果在项目业务逻辑中动态设置
 * 消息发送确认机制的逻辑的话就会出现上述的异常。
 * 问题的解决
 * 为了解决这个问题，我们需要单独创建一个 RabbitTemplate 的 config 配置类，将 RabbitTemplate 设
 * 置为多例的，并单独封装消息发送者类（如果需要不同的发送确认逻辑就需要创建封装多个），在使用时，
 * 直接注入对应的消息发送者类即可。
 * <p>

 * Rabbitmq 多例配置
 *
 * @author eamon.zhang
 * @date 2020-04-02 8:45 AM
 */
@Slf4j
@Configuration
@Component
public class RabbitTemplateConfig implements RabbitTemplate.ConfirmCallback{

    @Autowired
    ConnectionFactory connectionFactory;

    /**
     * 如果需要在生产者需要消息发送后的回调，
     * 需要对rabbitTemplate设置ConfirmCallback对象，
     * 由于不同的生产者需要对应不同的ConfirmCallback，
     * 如果rabbitTemplate设置为单例bean，
     * 则所有的rabbitTemplate实际的ConfirmCallback为最后一次申明的ConfirmCallback。
     *
     * @return RabbitTemplate
     */
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setMessageConverter(new SerializerMessageConverter());
        return rabbitTemplate;
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println(correlationData);
        System.out.println(ack);
        System.out.println(cause);
    }

}