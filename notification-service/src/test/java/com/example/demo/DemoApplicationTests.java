package com.example.demo;

import com.example.demo.core.domain.model.KindOfNotification;
import com.example.demo.core.domain.model.Notification;
import com.example.demo.core.domain.service.impl.NotificationService;
import com.example.demo.core.domain.service.interfaces.INotificationRepository;
import com.example.demo.core.domain.service.interfaces.INotificationService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

//@SpringBootTest
class DemoApplicationTests {

/*
	@Test
	void contextLoads() {
	}

    private  INotificationService notificationService;

    @Test
    void sendMessageTest(){

        UUID userID = UUID.randomUUID();
        String download = "click here to download";
        UUID orderID = UUID.randomUUID();
        KindOfNotification welcomeKind = KindOfNotification.WELCOME;
        KindOfNotification orderKind = KindOfNotification.ORDER;

        Notification welcomeNotification = new Notification(userID, welcomeKind);
        Notification orderNotification = new Notification(userID, orderID, download, orderKind);

        notificationService.createNotification(welcomeNotification);
        notificationService.createNotification(orderNotification);
    }

 */
}
