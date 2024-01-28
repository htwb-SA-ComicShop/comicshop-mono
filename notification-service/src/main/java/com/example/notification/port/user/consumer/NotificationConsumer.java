package com.example.notification.port.user.consumer;


import com.example.notification.core.domain.model.KindOfNotification;
import com.example.notification.core.domain.model.Notification;
import com.example.notification.core.domain.service.interfaces.INotificationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class NotificationConsumer {

    ObjectMapper mapper = new ObjectMapper();

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationConsumer.class);

    @Autowired
    private INotificationService notificationService;

    /*
    @RabbitListener(queues = {"notification"})
    public void consume(String message){

        LOGGER.info(String.format("Received message -> %s", message));
        notificationService.getNotification(new UUID(1L, 2L));
    }

     */
    @RabbitListener(queues = {"orderInfo"})
    public void consumeOrderInfo(String orderInfo){

        LOGGER.info(String.format("Received message -> %s", orderInfo));

        try {
            JsonNode jsonNode = mapper.readTree(orderInfo);
            String orderId = jsonNode.has("orderId") ? jsonNode.get("orderId").asText() : null;
            String linkToInvoice = jsonNode.has("linkToInvoice") ? jsonNode.get("linkToInvoice").asText() : null;
            String linkToContent = jsonNode.has("linkToContent") ? jsonNode.get("linkToContent").asText() : null;
            String recipient = jsonNode.has("recipient") ? jsonNode.get("recipient").asText() : null;

            Notification notification = new Notification(recipient, orderId, linkToContent, linkToInvoice, KindOfNotification.ORDER);

            System.out.println("NOTIFICATION RECEIVED. BODY IS: " + notification.getBodyText());

            notificationService.createNotification(notification);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        //Notification notification = new Notification(orderInfo.getRecipient(), orderInfo.getId(), orderInfo.getLinkToContent(), orderInfo.getLinkToInvoice(), KindOfNotification.ORDER);
    }
}