package com.example.demo.port.user.controller;

import com.example.demo.core.domain.model.KindOfNotification;
import com.example.demo.core.domain.model.Notification;

import com.example.demo.core.domain.service.interfaces.INotificationService;
import com.example.demo.port.user.exception.NotificationNotFoundException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@CrossOrigin(origins = "http://localhost:5173")
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

    @GetMapping("/seed-database")
    public void seedDataBase() {
        // FIXME: Just needed for development -- delete before public release

        UUID userID = UUID.randomUUID();
        String download = "click here to download";
        UUID orderID = UUID.randomUUID();
        KindOfNotification welcomeKind = KindOfNotification.WELCOME;
        KindOfNotification orderKind = KindOfNotification.ORDER;

        Notification welcomeNotification = new Notification(userID, welcomeKind);
        Notification orderNotification = new Notification(userID, orderID, download, orderKind);

//        notificationService.createNotification(welcomeNotification);
        notificationService.createNotification(orderNotification);
    }

}
