package com.example.cart.core.domain.service.interfaces;

import com.example.cart.core.domain.model.Cart;
import com.example.cart.core.domain.model.CartItem;
import com.example.cart.core.domain.model.Order;
import com.example.cart.port.user.exception.CartItemNotFoundException;
import com.example.cart.port.user.exception.CartNotFoundException;

import java.util.List;
import java.util.UUID;

public interface ICartService {
    /**
     * creates a Cart
     * saves it in the database
     * returns it with a UUID
     * @param cart the cart which is to be created
     * @return a Cart with an id
     */
    Cart createCart(Cart cart);

    /**
     * adds a Cart Item to the Cart
     * @param item which is added to the cart
     */
    void addToCart(CartItem item);

    /**
     * removes a Card Item from the Cart
     * @param itemId of the item which is to be removed
     * @throws CartItemNotFoundException if there is no Cart Item with the given id in the Cart
     */
    void removeFromCart(UUID itemId) throws CartItemNotFoundException;

    /**
     * Transfers the Cart into an Order
     */
    Cart buyCart();

    /**
     * deletes a cart from the db
     * @param id of the cart which is to be deleted
     * @throws CartNotFoundException when there is no cart with that id
     */
    void deleteCart (UUID id) throws CartNotFoundException;

    /**
     * returns a cart
     * @param id username of the cart which is to be returned
     * @return the card with the specified username
     * @throws CartNotFoundException when there is no cart with that id
     */
    Order getCart(UUID id) throws CartNotFoundException;

    /**
     * reads all carts from the database
     * @return a list with all carts
     */
    List<Cart> getAllCarts();

    /**
     * updates the cart in the database
     * @param cart the cart new cart
     * @param id of the card which is to be updated
     * @return the updated cart from the db
     * @throws CartNotFoundException if there is no cart with that id
     */
    Cart updateCart(Cart cart, UUID id) throws CartNotFoundException;
}
