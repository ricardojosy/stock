package com.ricardojosyferreira.listeners;

import com.ricardojosyferreira.listeners.dto.ProductCreateEvent;
import com.ricardojosyferreira.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import static com.ricardojosyferreira.config.RabbitMqConfig.PRODUCT_QUEUE;

@Component
public class ProductCreateListener {

    private final Logger logger = LoggerFactory.getLogger(ProductCreateListener.class);

    @Autowired
    private ProductService productService;

    @RabbitListener(queues=PRODUCT_QUEUE)
    public void listen(Message<ProductCreateEvent> message) {
        logger.info("Message: {}", message);
        productService.createPproductByListener(message.getPayload());
    }

}
