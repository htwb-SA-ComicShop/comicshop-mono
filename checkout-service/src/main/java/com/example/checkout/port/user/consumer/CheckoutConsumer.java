package com.example.checkout.port.user.consumer;

import com.example.checkout.core.domain.service.interfaces.ICheckoutService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CheckoutConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckoutConsumer.class);

    @Autowired
    private ICheckoutService productService;

    @RabbitListener(queues = {"product"})
    public void consume(String message){

        LOGGER.info(String.format("Received message -> %s", message));
        productService.getProduct(new UUID(1L, 2L));
    }
}