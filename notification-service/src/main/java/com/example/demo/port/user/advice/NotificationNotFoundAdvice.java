package com.example.demo.port.user.advice;

import com.example.demo.port.user.exception.NotificationNotFoundException;
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
    String productNotFoundHandler(NotificationNotFoundException ex){
        return ex.getMessage();
    }

}
