package com.example.cart.port.user.controller;

import com.example.cart.core.domain.model.Cart;
import com.example.cart.core.domain.model.CartItem;
import com.example.cart.core.domain.model.SendOrderInfoToNotificationDTO;
import com.example.cart.core.domain.service.interfaces.ICartService;
import com.example.cart.port.notification.producer.AddOrderInfoProducer;
import com.example.cart.port.user.exception.CartNotFoundException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
public class CartController {

    @Autowired
    private ICartService cartService;

    @Autowired
    AddOrderInfoProducer addOrderInfoProducer;

    @PostMapping(path = "/cart")
    @ResponseStatus(HttpStatus.OK)
    public String create(@RequestBody String cart) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(cart);
            //ObjectReader reader = mapper.readerFor(new TypeReference<List<CartItem>>() {
            //});
            //String id = jsonNode.has("id") ? jsonNode.get("id").asText() : null;
            String username = jsonNode.has("username") ? jsonNode.get("username").asText() : null;
            //List<CartItem> cartItems = new ArrayList<>();
            //if(jsonNode.has("cartItems")) {
            //    cartItems = reader.readValue(jsonNode.get("cartItems"));
            //}
            //Double totalPrice = jsonNode.has("totalPrice") ? jsonNode.get("totalPrice").asDouble() : null;
            //String email = jsonNode.has("email") ? jsonNode.get("email").asText() : null; TODO read email
            //if (id==null) {
                Cart newCart = new Cart();
                newCart.setUsername(username);
                //newCart.setCartItems(cartItems);
                //newCart.setTotalPrice(totalPrice);
                UUID newCartId = cartService.createCart(newCart).getId();
                System.out.println("CART CREATED -> NEW ID: " + newCartId);



                return newCartId.toString();
            //}
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/cart/{id}")
    public @ResponseBody Cart getCart(@PathVariable String id) {
        System.out.println("IN GETCART, ID: " + id);
        Cart cart = cartService.getCart(UUID.fromString(id));
        if (cart == null) throw new CartNotFoundException(UUID.fromString(id));
        return cart;
    }

    @PutMapping(path = "/cart/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin(origins = "*")
    public @ResponseBody void updateCart(@RequestBody Cart cart, @PathVariable String id) {
        System.out.println("Updating cart: " + cart);
        cartService.updateCart(cart, UUID.fromString(id));
    }

    @DeleteMapping(path = "/cart/{id}")
    @CrossOrigin(origins = "*")
    public @ResponseBody void deleteCart(@PathVariable String id) {
        cartService.deleteCart(UUID.fromString(id));
    }


    @GetMapping("/carts")
    public @ResponseBody List<Cart> getCarts() {
        return cartService.getAllCarts();
    }

    @DeleteMapping(path = "/cart/items/{cartId}")
    @CrossOrigin("*")
    public @ResponseBody void deleteItemFromCart(@PathVariable String cartId, String itemId){
        cartService.removeFromCart(UUID.fromString(itemId), UUID.fromString(cartId));
    }

    @GetMapping(path = "/cart/buy-cart/{id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody void buyCart(@PathVariable String id) {

        cartService.buyCart(UUID.fromString(id));
        //TODO make real put in buy method
        String linkToContent = "linkToContent";
        String linkToInvoice = "linkToInvoice";
        String recipient = "adobe@gmx.net";
        UUID orderId = new UUID(1L, 2L);

        SendOrderInfoToNotificationDTO sendOrder = new SendOrderInfoToNotificationDTO(linkToContent, linkToInvoice, recipient, orderId.toString());
        addOrderInfoProducer.sendToNotification(sendOrder);
    }


    @GetMapping("/keycloak/userCartId/{user}")
    @CrossOrigin(origins = "*")
    public String getCartIdFromKeykloak(@PathVariable String user) throws IOException, InterruptedException {
        KeycloakAPI keycloakAPI = new KeycloakAPI();
        return keycloakAPI.getUserCartId(user);
    }

    @GetMapping("/keycloak/updateCartId/{userName}/{newCartId}")
    @CrossOrigin(origins = "*")
    public String updateKeycloakCartId(@PathVariable String userName, @PathVariable String newCartId) throws IOException, InterruptedException {
        KeycloakAPI keycloakAPI = new KeycloakAPI();
        return keycloakAPI.updateCartId(userName, newCartId);
    }

    @GetMapping("/keycloak/resetPassword/{username}/{newPassword}")
    @CrossOrigin(origins = "*")
    public String resetKeycloakPassword(@PathVariable String username, @PathVariable String newPassword) throws IOException, InterruptedException {
        KeycloakAPI keycloakAPI = new KeycloakAPI();
        return keycloakAPI.resetPassword(username, newPassword);
    }

    @GetMapping("/test")
    @CrossOrigin(origins = "*")
    public String test() {
        return "TEST";
    }
    
}
