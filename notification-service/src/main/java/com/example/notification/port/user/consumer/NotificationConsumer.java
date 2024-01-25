package com.example.notification.port.user.consumer;


import com.example.notification.core.domain.model.KindOfNotification;
import com.example.notification.core.domain.model.Notification;
import com.example.notification.core.domain.service.interfaces.INotificationService;
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
    @RabbitListener(queues = {"notification.order"})
    public void consumeOrderInfo(String orderInfo){

        LOGGER.info(String.format("Received message -> %s", orderInfo));


        //Notification notification = new Notification(orderInfo.getRecipient(), orderInfo.getId(), orderInfo.getLinkToContent(), orderInfo.getLinkToInvoice(), KindOfNotification.ORDER);
        //notificationService.createNotification(notification);
    }
}