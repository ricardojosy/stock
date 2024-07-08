package com.ricardojosyferreira.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ricardojosyferreira.config.RabbitMqConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitService {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendProduct(Object message) throws JsonProcessingException {
        amqpTemplate.convertAndSend(RabbitMqConfig.PRODUCT_QUEUE, message);
    }
}
