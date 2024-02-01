package com.example.cart.port.user.controller;

import com.example.cart.core.domain.model.Cart;
import com.example.cart.core.domain.model.SendOrderInfoToNotificationDTO;
import com.example.cart.core.domain.service.interfaces.ICartService;
import com.example.cart.port.notification.producer.AddOrderInfoProducer;
import com.example.cart.port.user.exception.CartNotFoundException;

import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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
    public @ResponseBody void buyCart(@PathVariable UUID id) throws StripeException {//TODO return different http status instead of an exception


        Cart boughtCart = cartService.buyCart(id);

        SendOrderInfoToNotificationDTO sendOrder =
                new SendOrderInfoToNotificationDTO(boughtCart.getLinkToContent(),
                boughtCart.getLinkToInvoice(), boughtCart.getEmail(), boughtCart.getId().toString());
        addOrderInfoProducer.sendToNotification(sendOrder);
    }

}
