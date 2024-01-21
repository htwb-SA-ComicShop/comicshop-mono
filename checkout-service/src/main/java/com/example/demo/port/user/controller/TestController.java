package com.example.demo.port.user.controller;

import com.example.demo.port.user.producer.CheckoutProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

	@Autowired
    CheckoutProducer checkoutProducer;

	@GetMapping("/product-queue")
	public void triggerProductQueue() {
		try {
			checkoutProducer.sendMessage("product");
		} catch (Exception e) {
		}
	}


}
