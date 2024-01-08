package com.example.demo.core.domain.service.interfaces;

import com.example.demo.core.domain.model.Notification;
import com.example.demo.port.user.exception.NotificationNotFoundException;
import jakarta.mail.SendFailedException;

import java.util.List;
import java.util.UUID;


public interface INotificationService {

    /**
     * Create and send Message
     * @param notification
     */
    void createNotification(Notification notification);

    Notification updateNotification(Notification product) throws NotificationNotFoundException;

    void deleteNotification(UUID id) throws NotificationNotFoundException;

    Notification getNotification(UUID id) throws NotificationNotFoundException;

    List<Notification> getAllNotifications();
}
