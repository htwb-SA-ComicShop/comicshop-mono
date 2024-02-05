package com.example.notification.core.domain.service.interfaces;

import com.example.notification.core.domain.model.Notification;
import com.example.notification.port.user.exception.NotificationNotFoundException;

import java.util.List;
import java.util.UUID;


/**
 * Service interface for managing notifications.
 */
public interface INotificationService {

    /**
     * Creates a new notification.
     *
     * @param notification The notification to be created.
     */
    void createNotification(Notification notification);

    /**
     * Updates an existing notification.
     *
     * @param notification The updated notification details.
     * @return The updated notification.
     * @throws NotificationNotFoundException If the notification with the given ID is not found.
     */
    Notification updateNotification(Notification notification) throws NotificationNotFoundException;

    /**
     * Deletes a notification by its unique identifier.
     *
     * @param id The unique identifier of the notification to be deleted.
     * @throws NotificationNotFoundException If the notification with the given ID is not found.
     */
    void deleteNotification(UUID id) throws NotificationNotFoundException;

    /**
     * Retrieves a notification by its unique identifier.
     *
     * @param id The unique identifier of the notification.
     * @return The retrieved notification.
     * @throws NotificationNotFoundException If the notification with the given ID is not found.
     */
    Notification getNotification(UUID id) throws NotificationNotFoundException;

    /**
     * Retrieves a list of all notifications.
     *
     * @return List of notifications.
     */
    List<Notification> getAllNotifications();
}
