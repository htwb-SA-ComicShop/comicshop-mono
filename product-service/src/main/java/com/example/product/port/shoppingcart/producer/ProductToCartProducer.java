package com.example.product.port.shoppingcart.producer;

import com.example.product.core.domain.model.AddToCartDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ProductToCartProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductToCartProducer.class);
    private final RabbitTemplate rabbitTemplate;
    @Value("product_exchange")
    private String exchange;
    @Value("cart_item_routing_key")
    private String routingKey;

    public ProductToCartProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendToCart(AddToCartDTO cartItem) {
        rabbitTemplate.convertAndSend(exchange, routingKey, cartItem.toJson());
        LOGGER.info(String.format("Message sent -> %s by %s", cartItem.getProductName(), cartItem.getUsername()));
    }
}