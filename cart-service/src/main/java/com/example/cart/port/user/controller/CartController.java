package com.example.cart.port.user.controller;

import com.example.cart.core.domain.model.Cart;
import com.example.cart.core.domain.model.SendOrderInfoToNotificationDTO;
import com.example.cart.core.domain.service.interfaces.ICartService;
import com.example.cart.port.notification.producer.AddOrderInfoProducer;
import com.example.cart.port.user.exception.CartNotFoundException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;


@RestController
public class CartController {
    @Autowired
    AddOrderInfoProducer addOrderInfoProducer;
    @Autowired
    private ICartService cartService;

    /**
     * Create a new shopping cart.
     *
     * @param cart JSON representation of the cart.
     * @return ID of the created cart.
     */
    @PostMapping(path = "/cart")
    @ResponseStatus(HttpStatus.OK)
    public String create(@RequestBody String cart) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(cart);

            String username = jsonNode.has("username") ? jsonNode.get("username").asText() : null;

            Cart newCart = new Cart();
            newCart.setUsername(username);

            UUID newCartId = cartService.createCart(newCart).getId();
            return newCartId.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieve a shopping cart by ID.
     *
     * @param id Unique identifier of the shopping cart.
     * @return Retrieved cart.
     */
    @GetMapping("/cart/{id}")
    public @ResponseBody Cart getCart(@PathVariable String id) {
        Cart cart = cartService.getCart(UUID.fromString(id));
        if (cart == null) throw new CartNotFoundException(UUID.fromString(id));
        return cart;
    }

    /**
     * Update an existing shopping cart.
     *
     * @param cart Updated cart details.
     * @param id   Unique identifier of the shopping cart to be updated.
     */
    @PutMapping(path = "/cart/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin(origins = "*")
    public @ResponseBody void updateCart(@RequestBody Cart cart, @PathVariable String id) {
        cartService.updateCart(cart, UUID.fromString(id));
    }

    /**
     * Delete a shopping cart by ID.
     *
     * @param id Unique identifier of the shopping cart to be deleted.
     */
    @DeleteMapping(path = "/cart/{id}")
    @CrossOrigin(origins = "*")
    public @ResponseBody void deleteCart(@PathVariable String id) {
        cartService.deleteCart(UUID.fromString(id));
    }


    /**
     * Retrieve a list of all shopping carts.
     *
     * @return List of shopping carts.
     */
    @GetMapping("/carts")
    public @ResponseBody List<Cart> getCarts() {
        return cartService.getAllCarts();
    }

    /**
     * Delete an item from a shopping cart.
     *
     * @param cartId Unique identifier of the cart.
     * @param itemId Unique identifier of the item to be removed.
     */
    @DeleteMapping(path = "/cart/items/{itemId}")
    @CrossOrigin("*")
    public @ResponseBody void deleteItemFromCart(@PathVariable String itemId, @RequestBody String cartId) {
        cartService.removeFromCart(UUID.fromString(itemId), UUID.fromString(cartId));
    }

    /**
     * Purchase a shopping cart.
     *
     * @param id Unique identifier of the shopping cart to be purchased.
     */
    @GetMapping(path = "/cart/buy-cart/{id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody void buyCart(@PathVariable String id) {

        Cart boughtCart;
        try {
            boughtCart = cartService.checkoutCart(UUID.fromString(id));
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }
        boughtCart.setEmail("adobe@gmx.net");
        SendOrderInfoToNotificationDTO sendOrder = new SendOrderInfoToNotificationDTO(boughtCart.getLinkToContent(), boughtCart.getLinkToInvoice(), boughtCart.getEmail(), boughtCart.getId().toString());
        addOrderInfoProducer.sendToNotification(sendOrder);
    }


    /**
     * gets the cart id from a specific user from keycloak
     *
     * @param user the username to whom the cart belongs
     * @return the cart id from this user
     * @throws IOException
     * @throws InterruptedException
     */
    @GetMapping("/keycloak/userCartId/{user}")
    @CrossOrigin(origins = "*")
    public String getCartIdFromKeykloak(@PathVariable String user) throws IOException, InterruptedException {
        KeycloakAPI keycloakAPI = new KeycloakAPI();
        return keycloakAPI.getUserCartId(user);
    }

    /**
     * updates cart from user
     *
     * @param userName  user's username
     * @param newCartId id from cart to be saved for user
     * @return "new cart_id: " + the new cartId if successful, "ERROR!" otherwise
     * @throws IOException
     * @throws InterruptedException
     */
    @GetMapping("/keycloak/updateCartId/{userName}/{newCartId}")
    @CrossOrigin(origins = "*")
    public String updateKeycloakCartId(@PathVariable String userName, @PathVariable String newCartId) throws IOException, InterruptedException {
        KeycloakAPI keycloakAPI = new KeycloakAPI();
        return keycloakAPI.updateCartId(userName, newCartId);
    }

    /**
     * resets user password
     *
     * @param username    username from user
     * @param newPassword new password to be saved for user
     * @return "PASSWORD CHANGED!" if password successfully changed, "ERROR" otherwise
     * @throws IOException
     * @throws InterruptedException
     */
    @GetMapping("/keycloak/resetPassword/{username}/{newPassword}")
    @CrossOrigin(origins = "*")
    public String resetKeycloakPassword(@PathVariable String username, @PathVariable String newPassword) throws IOException, InterruptedException {
        KeycloakAPI keycloakAPI = new KeycloakAPI();
        return keycloakAPI.resetPassword(username, newPassword);
    }

}
