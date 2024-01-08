package com.example.demo.core.domain.service.impl;

import com.example.demo.core.domain.model.Notification;
import com.example.demo.core.domain.service.interfaces.INotificationService;
import com.example.demo.core.domain.service.interfaces.INotificationRepository;
import com.example.demo.port.user.exception.NotificationNotFoundException;
import jakarta.mail.SendFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
@Component
public class NotificationService implements INotificationService {
    @Autowired
    private final INotificationRepository notificationRepository;

    private final EmailService emailService;
    //private final SMSService smsService;

    @Override
    public void createNotification(Notification notification) {
        if (sendNotification(notification)) {
            System.out.println("EMAIL SUCCESSFULLY SENT!");
            notificationRepository.save(notification);
        }
        System.out.println("EMAIL NOT SENT!");
    }

    @Override
    public Notification updateNotification(Notification product) throws NotificationNotFoundException {
        return null;
    }

    @Override
    public void deleteNotification(UUID id) throws NotificationNotFoundException {
        notificationRepository
                .findById(id)
                .orElseThrow(() -> new NotificationNotFoundException(id));
        notificationRepository.deleteById(id);
    }

    @Override
    public Notification getNotification(UUID id) throws NotificationNotFoundException {
        if (id == null) {
            throw new IllegalArgumentException("Product ID is invalid.");
        }
        return notificationRepository
                .findById(id)
                .orElseThrow(() -> new NotificationNotFoundException(id));
    }

    @Override
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    private boolean sendNotification(Notification notification) {
        /*
        //TODO how to access keycloak???
        UserResource userResource = keycloack.realm(REALM).users().get(notification.getRecipientID());

        if (userResource.getEmail()!=null&&userResource.isEmailVerified()){ //TODO: should email also be verified?
            //TODO: check if user has phone number and send sms?
              try {
                  emailService.sendEmail(userResource.getEmail(), notification.getSubject(), notification.getBodyText());
                  return true;
              } catch (SendFailedException e){
                  return false;
              }
        }
         */
        try {
            emailService.sendEmail("pedroaasanchez@gmail.com", notification.getSubject(), notification.getBodyText());
            return true;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        //return false;
    }
}
