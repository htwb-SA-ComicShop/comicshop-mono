package com.example.demo.port.user.controller;

import com.example.demo.port.shoppingcart.producer.AddProductProducer;
import com.example.demo.port.user.producer.ProductProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

	@Autowired
	ProductProducer productProducer;

	@GetMapping("/product-queue")
	public void triggerProductQueue() {
		try {
			productProducer.sendMessage("product");
		} catch (Exception e) {
		}
	}


}
