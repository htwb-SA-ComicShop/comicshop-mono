package com.example.cart.port.user.consumer;

import com.example.cart.core.domain.model.Cart;
import com.example.cart.core.domain.model.CartItem;
import com.example.cart.core.domain.service.interfaces.ICartService;
import com.example.cart.port.user.controller.KeycloakAPI;
import com.example.cart.port.user.exception.CartNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

import static java.lang.Double.NaN;

@Service
public class AddToCartConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddToCartConsumer.class);

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private ICartService cartService;

    @RabbitListener(queues = {"cart_item"})
    public void consume(String addToCart){

        LOGGER.info(String.format("Received message -> %s", addToCart));


        JSONObject cartItem = new JSONObject(addToCart);
        CartItem item = new CartItem(
                UUID.fromString(cartItem.get("productId").toString()),
                cartItem.get("productName").toString(),
                cartItem.get("author").toString(),
                Double.parseDouble(cartItem.get("price").toString()),
                cartItem.get("imgUrl").toString(),
                ""); //TODO add real link to product?

        if (item.getProductId() == null || item.getPrice() < 0.0 || Double.isNaN(item.getPrice())){
            throw new NullPointerException("product id and price have to be valid");
        }

        /*
        String cartId;

        /*
        String username = cartItem.getString("username");
        KeycloakAPI keycloakAPI = new KeycloakAPI();

        try {
            cartId = keycloakAPI.getUserCartId(username);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (cartId==null) {
            throw new CartNotFoundException(null);
        }
        */
        //TODO remove hard coded cart
        Cart updatedCart = cartService.getCart(UUID.fromString("00e8df61-c08f-47d4-96cb-c70fad2973f0"));
        updatedCart.setEmail(cartItem.get("email").toString());;
        cartService.addToCart(item, updatedCart.getId());
        System.out.println("CART ITEM RECEIVED. ID IS: " + item.getProductId());
    }
}