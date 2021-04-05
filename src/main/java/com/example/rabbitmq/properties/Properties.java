package com.example.rabbitmq.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author hcg
 * @version 1.0
 * @date 2021/2/6 12:09
 */
@Component
@ConfigurationProperties(prefix = "simple", value = "application.properties")
public class Properties {

    @Value("${simple.queue}")
    private String simpleQueue;

    public String getSimpleQueue() {
        return simpleQueue;
    }

    public void setSimpleQueue(String simpleQueue) {
        this.simpleQueue = simpleQueue;
    }
}
