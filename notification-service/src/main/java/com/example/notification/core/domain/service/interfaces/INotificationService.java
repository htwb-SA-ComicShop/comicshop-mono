package com.example.notification.core.domain.service.interfaces;

import com.example.notification.core.domain.model.Notification;
import com.example.notification.port.user.exception.NotificationNotFoundException;

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
