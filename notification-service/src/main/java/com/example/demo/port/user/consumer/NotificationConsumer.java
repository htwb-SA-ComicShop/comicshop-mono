package com.example.demo.port.user.consumer;

import com.example.demo.core.domain.service.interfaces.INotificationService;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class NotificationConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationConsumer.class);

    @Autowired
    private INotificationService notificationService;

    @RabbitListener(queues = {"notification"})
    public void consume(String message){

        LOGGER.info(String.format("Received message -> %s", message));
        notificationService.getNotification(new UUID(1L, 2L));
    }
}