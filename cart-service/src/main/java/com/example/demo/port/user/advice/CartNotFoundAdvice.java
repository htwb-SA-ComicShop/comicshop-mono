package com.example.demo.port.user.advice;

import com.example.demo.port.user.exception.CartNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CartNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(value = CartNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String productNotFoundHandler(CartNotFoundException ex){
        return ex.getMessage();
    }

}
