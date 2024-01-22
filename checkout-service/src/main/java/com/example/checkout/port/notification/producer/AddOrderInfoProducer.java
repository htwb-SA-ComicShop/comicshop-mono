package com.example.checkout.port.notification.producer;

import com.example.checkout.core.domain.model.SendOrderInfoToNotificationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AddOrderInfoProducer {

    @Value("product_exchange")
    private String exchange;

    @Value("cart_item_routing_key")
    private String routingKey;

    private static final Logger LOGGER = LoggerFactory.getLogger(AddOrderInfoProducer.class);

    private final RabbitTemplate rabbitTemplate;

    public AddOrderInfoProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendToNotification(SendOrderInfoToNotificationDTO orderInfo){
        rabbitTemplate.convertAndSend(exchange, routingKey, orderInfo);
        LOGGER.info(String.format("Message sent to %s invoice: %s content: %s ", orderInfo.getRecipient(), orderInfo.getLinkToInvoice(), orderInfo.getLinkToContent()));
    }
}