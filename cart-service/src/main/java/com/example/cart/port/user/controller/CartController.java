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
            ObjectReader reader = mapper.readerFor(new TypeReference<List<CartItem>>() {
            });
            //String id = jsonNode.has("id") ? jsonNode.get("id").asText() : null;
            String username = jsonNode.has("username") ? jsonNode.get("username").asText() : null;
            List<CartItem> cartItems = new ArrayList<>();
            if(jsonNode.has("cartItems")) {
                cartItems = reader.readValue(jsonNode.get("cartItems"));
            }
            Double totalPrice = jsonNode.has("totalPrice") ? jsonNode.get("totalPrice").asDouble() : null;
            //String email = jsonNode.has("email") ? jsonNode.get("email").asText() : null; TODO read email
            //if (id==null) {
                Cart newCart = new Cart();
                newCart.setUsername(username);
                newCart.setCartItems(cartItems);
                newCart.setTotalPrice(totalPrice);
                UUID newCartId = cartService.createCart(newCart).getId();
                System.out.println("CART CREATED -> NEW ID: " + newCartId);

                return newCartId.toString();
            //}
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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


    @GetMapping("/carts")
    public @ResponseBody List<Cart> getCarts() {
        return cartService.getAllCarts();
    }

    @DeleteMapping(path = "/cart/items/{itemId}")
    @CrossOrigin("*")
    public @ResponseBody void deleteItemFromCart(UUID cartId, @PathVariable UUID itemId){
        cartService.removeFromCart(itemId, cartId);
    }

    @GetMapping(path = "/cart/buy-cart/{id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody void buyCart(@PathVariable UUID id) {

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
