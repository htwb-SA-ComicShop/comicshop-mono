package com.example.cart.port.user.controller;

import com.example.cart.core.domain.model.Cart;
import com.example.cart.core.domain.model.SendOrderInfoToNotificationDTO;
import com.example.cart.core.domain.service.interfaces.ICartService;
import com.example.cart.port.notification.producer.AddOrderInfoProducer;
import com.example.cart.port.user.exception.CartNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class CartController {
    @Autowired
    private ICartService cartService;

    @Autowired
    AddOrderInfoProducer addOrderInfoProducer;

    @PostMapping(path = "/cart")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody void create(@RequestBody Cart cart) {
        Cart newCart = new Cart();
        newCart.setUsername(cart.getUsername());
        newCart.setCartItems(cart.getCartItems());
        newCart.setTotalPrice(cart.getTotalPrice());
        newCart.setBoughtAt(cart.getBoughtAt());
        cartService.createCart(newCart);
    }

    @GetMapping("/cart/{id}")
    public Cart getCart(@PathVariable UUID id) {
        Cart cart = cartService.getCart(id);
        if (cart == null) throw new CartNotFoundException(id);
        return cart;
    }

    @PutMapping(path = "/cart/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin(origins = "*")
    public @ResponseBody void updateCart(@RequestBody Cart cart, @PathVariable UUID id) {
        System.out.println("Updating cart: " + cart);
        cartService.updateCart(cart, id);
    }

    @DeleteMapping(path = "/cart/{id}")
    @CrossOrigin(origins = "*")
    public @ResponseBody void deleteCart(@PathVariable UUID id) {
        cartService.deleteCart(id);
    }


    @GetMapping("/cart")
    public @ResponseBody List<Cart> getCarts() {
        return cartService.getAllCarts();
    }

    @GetMapping(path = "/cart/buy-cart/{id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody void buyCart(@PathVariable UUID id) {
        //TODO maybe get cart as parameter @RequestBody?
        //JwtAuthenticationToken authToken = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        //Map<String, Object> tokenAttributes = authToken.getTokenAttributes();
        //String userName = (String) tokenAttributes.get("preferred_username");
        //String email = (String) tokenAttributes.get("email");
        //System.out.println("Sending to Notification: " + order.getId());
        //System.out.println("userName: " + userName);
        //System.out.println("email: " + email);

        cartService.buyCart(id);
        //TODO make real put in buy method
        String linkToContent = "linkToContent";
        String linkToInvoice = "linkToInvoice";
        String recipient = "adobe@gmx.net";
        UUID orderId = new UUID(1L, 2L);

        SendOrderInfoToNotificationDTO sendOrder = new SendOrderInfoToNotificationDTO(linkToContent, linkToInvoice, recipient, orderId.toString());
        addOrderInfoProducer.sendToNotification(sendOrder);
    }

}
