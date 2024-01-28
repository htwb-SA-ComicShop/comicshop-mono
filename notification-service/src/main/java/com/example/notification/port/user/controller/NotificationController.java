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

    @PostMapping(path = "/notification")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody void create(@RequestBody Notification notification) {
        notificationService.createNotification(notification);
    }

    @GetMapping("/notification/{id}")
    public Notification getNotification(@PathVariable UUID id) {
        Notification product = notificationService.getNotification(id);

        if (product == null) {
            throw new NotificationNotFoundException(id);
        }

        return product;
    }

    @DeleteMapping(path = "/notification")
    public @ResponseBody void deleteProduct(@RequestBody UUID productId) {
        notificationService.deleteNotification(productId);
    }

    @GetMapping("/notifications")
    public @ResponseBody List<Notification> getNotifications() {
        return notificationService.getAllNotifications();
    }


}
