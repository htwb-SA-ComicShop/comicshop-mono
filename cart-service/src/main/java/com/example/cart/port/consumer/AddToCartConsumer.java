package com.example.cart.port.consumer;

import com.example.cart.core.domain.service.interfaces.ICartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddToCartConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddToCartConsumer.class);

    @Autowired
    private ICartService cartService;

    @RabbitListener(queues = {"cart_item"})
    public void consume(String addToCart){

        LOGGER.info(String.format("Received message -> %s", addToCart));

        //TODO how to map the cart with the user?
        //TODO add item to cart
    }
}