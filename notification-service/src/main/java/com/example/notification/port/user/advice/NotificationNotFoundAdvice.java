package com.example.notification.port.user.advice;

import com.example.notification.port.user.exception.NotificationNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class NotificationNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(value = NotificationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String notificationNotFoundHandler(NotificationNotFoundException ex) {
        return ex.getMessage();
    }

}
