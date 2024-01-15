package com.example.demo.port.productservice.consumer;

import com.example.demo.core.domain.model.AddToCartDTO;
import com.example.demo.core.domain.service.interfaces.ICartService;
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
    public void consume(AddToCartDTO dto){

        LOGGER.info(String.format("Received message -> %s by %s", dto.getUsername(), dto.getProductName()));
    }
}