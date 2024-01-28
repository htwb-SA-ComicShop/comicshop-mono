package com.example.notification.port.user.controller;

//import com.example.notification.port.shoppingcart.producer.UpdateProdcutProducer;
//import com.example.notification.port.user.producer.ProductProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

	/*@Autowired
    ProductProducer productProducer;

	@Autowired
    UpdateProdcutProducer updateProdcutProducer;
*/
	@GetMapping("/notification-queue")
	public void triggerNotificationQueue() {
		try {
			//productProducer.sendMessage("product");
		} catch (Exception e) {
		}
	}

	@GetMapping("/notification-update-queue")
	public void triggerNotificationUpdateQueue() {
		try {
			//updateProdcutProducer.sendMessage("update");
		} catch (Exception e) {
		}
	}


}
