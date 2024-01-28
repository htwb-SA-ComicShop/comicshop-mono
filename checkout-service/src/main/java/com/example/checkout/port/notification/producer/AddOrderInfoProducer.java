package com.example.checkout.port.notification.producer;

import com.example.checkout.core.domain.model.SendOrderInfoToNotificationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AddOrderInfoProducer {

    @Value("order_exchange")
    private String exchange;

    @Value("order_info_routing_key")
    private String routingKey;

    private static final Logger LOGGER = LoggerFactory.getLogger(AddOrderInfoProducer.class);

    private final RabbitTemplate rabbitTemplate;

    public AddOrderInfoProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendToNotification(SendOrderInfoToNotificationDTO orderInfo){
        rabbitTemplate.convertAndSend(exchange, routingKey, orderInfo.toJson());
        LOGGER.info(String.format("Message sent: %s", orderInfo.toJson()));
    }
}