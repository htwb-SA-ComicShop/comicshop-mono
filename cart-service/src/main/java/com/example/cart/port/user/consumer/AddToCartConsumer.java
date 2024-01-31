package com.example.cart.port.user.consumer;

import com.example.cart.core.domain.model.Cart;
import com.example.cart.core.domain.model.CartItem;
import com.example.cart.core.domain.service.interfaces.ICartService;
import com.example.cart.port.user.exception.CartNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        try {
            JsonNode jsonNode = mapper.readTree(addToCart);
            String cartId = jsonNode.has("cartId") ? jsonNode.get("cartId").asText() : null;
            String productName = jsonNode.has("productName") ? jsonNode.get("productName").asText() : null;
            String productId = jsonNode.has("productId") ? jsonNode.get("productId").asText() : null;
            String username = jsonNode.has("username") ? jsonNode.get("username").asText() : null;
            String email = jsonNode.has("email") ? jsonNode.get("email").asText() : null;
            String author = jsonNode.has("author") ? jsonNode.get("author").asText() : null;
            String imgUrl = jsonNode.has("imgUrl") ? jsonNode.get("imgUrl").asText() : null;
            Double price = jsonNode.has("price") ? jsonNode.get("price").asDouble() : null;

            if (cartId==null) {
                throw new CartNotFoundException(null);
            }
            if (productId == null || price == null || Double.isNaN(price)){
                throw new NullPointerException("product id and price have to be valid");
            }

                CartItem item = new CartItem(UUID.fromString(productId), productName, author, price, imgUrl, "");
                cartService.addToCart(item, UUID.fromString(cartId));
                System.out.println("CARTITEM RECEIVED. ID IS: " + item.getId());

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}