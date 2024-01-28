package com.example.checkout.port.user.controller;

import com.example.checkout.port.user.producer.CheckoutProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

	@Autowired
	CheckoutProducer CheckoutProducer;

	@GetMapping("/product-queue")
	public void triggerProductQueue() {
		try {
			CheckoutProducer.sendMessage("product");
		} catch (Exception e) {
		}
	}


}
