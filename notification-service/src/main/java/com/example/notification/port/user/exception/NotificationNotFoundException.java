package com.example.notification.port.user.exception;

import java.util.UUID;

public class NotificationNotFoundException extends RuntimeException {
    public NotificationNotFoundException(UUID id) {
        super("Could not find notification " +id);
    }
}

