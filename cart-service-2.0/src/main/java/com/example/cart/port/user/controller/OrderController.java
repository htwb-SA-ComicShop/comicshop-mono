package com.example.cart.port.user.controller;

import com.example.cart.core.domain.model.Cart;
import com.example.cart.core.domain.model.SendOrderInfoToNotificationDTO;
import com.example.cart.core.domain.model.Order;
import com.example.cart.core.domain.service.interfaces.ICartService;
import com.example.cart.port.notification.producer.AddOrderInfoProducer;
import com.example.cart.port.user.exception.CartNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
public class OrderController {
//TODO refactor to CartController
    @Autowired
    private ICartService cartService;

    @Autowired
    AddOrderInfoProducer addOrderInfoProducer;

    @PostMapping(path = "/order")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody void create(@RequestBody Cart cart) {
        Cart newCart = new Cart();
        newCart.setUsername(cart.getUsername());
        newCart.setCartItems(cart.getCartItems());
        newCart.setTotalPrice(cart.getTotalPrice());
        newCart.setBoughtAt(cart.getBoughtAt());
        cartService.createCart(newCart);
    }

    @GetMapping("/order/{id}")
    public Order getOrder(@PathVariable UUID id) {
        Order order = cartService.getCart(id);
        if (order == null) throw new CartNotFoundException(id);
        return order;
    }

    @GetMapping("/orders")
    public @ResponseBody List<Cart> getProducts() {
        return cartService.getAllCarts();
    }

    @GetMapping(path = "/seed-database")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody void sendToNotification() {
        //TODO maybe get cart as parameter @RequestBody?
        //JwtAuthenticationToken authToken = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        //Map<String, Object> tokenAttributes = authToken.getTokenAttributes();
        //String userName = (String) tokenAttributes.get("preferred_username");
        //String email = (String) tokenAttributes.get("email");
        //System.out.println("Sending to Notification: " + order.getId());
        //System.out.println("userName: " + userName);
        //System.out.println("email: " + email);

        //TODO make real strings
        String linkToContent = "linkToContent";
        String linkToInvoice = "linkToInvoice";
        String recipient = "adobe@gmx.net";
        UUID orderId = new UUID(1L, 2L);

        SendOrderInfoToNotificationDTO sendOrder = new SendOrderInfoToNotificationDTO(linkToContent, linkToInvoice, recipient, orderId.toString());
       //TODO change addCheckoutProducer
        addOrderInfoProducer.sendToNotification(sendOrder);
    }

}
