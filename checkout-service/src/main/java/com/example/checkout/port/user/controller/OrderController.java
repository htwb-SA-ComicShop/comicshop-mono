package com.example.checkout.port.user.controller;

import com.example.checkout.core.domain.model.SendOrderInfoToNotificationDTO;
import com.example.checkout.core.domain.model.Order;
import com.example.checkout.core.domain.service.interfaces.ICheckoutService;
import com.example.checkout.port.notification.producer.AddOrderInfoProducer;
import com.example.checkout.port.user.exception.ProductNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class OrderController {

    @Autowired
    private ICheckoutService checkoutService;

    @Autowired
    AddOrderInfoProducer addOrderInfoProducer;

    @PostMapping(path = "/order")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody void create(@RequestBody Order order) {
        Order newOrder = new Order(
                order.getPrice(),
                order.getDate()
                //order.getContent();
        );
        checkoutService.createOrder(newOrder);
    }

    @GetMapping("/order/{id}")
    public Order getOrder(@PathVariable UUID id) {
        Order order = checkoutService.getOrder(id);
        if (order == null) throw new ProductNotFoundException(id);
        return order;
    }

    @GetMapping("/orders")
    public @ResponseBody List<Order> getProducts() {
        return checkoutService.getAllOrder();
    }

    @PostMapping(path = "/send-to-notification")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody void sendToNotification(@RequestBody Order order) {
        JwtAuthenticationToken authToken = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        Map<String, Object> tokenAttributes = authToken.getTokenAttributes();
        String userName = (String) tokenAttributes.get("preferred_username");
        String email = (String) tokenAttributes.get("email");
        System.out.println("Sending to Notification: " + order.getId());
        System.out.println("userName: " + userName);
        System.out.println("email: " + email);

        //TODO make real strings
        String linkToContent = "linkToContent";
        String linkToInvoice = "linkToInvoice";
        String recipient = "adobe@gmx.net";

        SendOrderInfoToNotificationDTO sendOrder = new SendOrderInfoToNotificationDTO(linkToContent, linkToInvoice, recipient);
       //TODO change addCheckoutProducer
        addOrderInfoProducer.sendToNotification(sendOrder);
    }

}
