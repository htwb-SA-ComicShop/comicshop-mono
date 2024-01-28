package com.example.cart.port.user.controller;

import com.example.cart.core.domain.service.interfaces.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartController {

    @Autowired
    private ICartService cartService;

    // TODO: Implement CRUD endpoints for CartController

    @GetMapping("/test")
    public String test() {
        return "Hello World";
    }
}
