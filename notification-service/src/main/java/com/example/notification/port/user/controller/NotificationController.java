package com.example.notification.port.user.controller;

import com.example.notification.core.domain.model.KindOfNotification;
import com.example.notification.core.domain.model.Notification;

import com.example.notification.core.domain.service.interfaces.INotificationService;
import com.example.notification.port.user.exception.NotificationNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class NotificationController {

    @Autowired
    private INotificationService notificationService;

    /**
     * Creates a new notification.
     *
     * @param notification The notification to be created.
     */
    @PostMapping(path = "/notification")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody void create(@RequestBody Notification notification) {
        notificationService.createNotification(notification);
    }

    /**
     * Retrieves a notification by its unique identifier.
     *
     * @param id The unique identifier of the notification.
     * @return The retrieved notification.
     * @throws NotificationNotFoundException If the notification with the given ID is not found.
     */
    @GetMapping("/notification/{id}")
    public Notification getNotification(@PathVariable UUID id) {
        Notification notification = notificationService.getNotification(id);

        if (notification == null) {
            throw new NotificationNotFoundException(id);
        }

        return notification;
    }

    /**
     * Deletes a notification by its unique identifier.
     *
     * @param productId The unique identifier of the notification to be deleted.
     */
    @DeleteMapping(path = "/notification")
    public @ResponseBody void deleteProduct(@RequestBody UUID productId) {
        notificationService.deleteNotification(productId);
    }

    /**
     * Retrieves a list of all notifications.
     *
     * @return List of notifications.
     */
    @GetMapping("/notifications")
    public @ResponseBody List<Notification> getNotifications() {
        return notificationService.getAllNotifications();
    }


}
