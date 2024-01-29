package com.example.cart.core.domain.service.impl;

import com.example.cart.core.domain.model.Cart;
import com.example.cart.core.domain.model.CartItem;
import com.example.cart.core.domain.model.Order;
import com.example.cart.core.domain.service.interfaces.ICartRepository;
import com.example.cart.core.domain.service.interfaces.ICartService;
import com.example.cart.port.user.exception.CartItemNotFoundException;
import com.example.cart.port.user.exception.CartNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class CartService implements ICartService {
    @Autowired
    private final ICartRepository cartRepository;
    private Cart cart;

    @Override
    public Cart createCart(Cart cart) {
        this.cart = cart;

        //TODO safe in db
        return cart;
    }

    @Override
    public void addToCart(CartItem item) {
        double newTotalPrice = cart.getTotalPrice();
        newTotalPrice += item.getPrice();
        cart.setTotalPrice(newTotalPrice);

        HashMap<UUID, CartItem> cartItems = cart.getCartItems();
        cartItems.put(item.getId(), item);
        cart.setCartItems(cartItems);
    }

    @Override
    public void removeFromCart(UUID itemId) throws CartItemNotFoundException {
        HashMap<UUID, CartItem> newCartItems = cart.getCartItems();
        if (newCartItems.containsKey(itemId)) {
            double newTotalPrice = cart.getTotalPrice();
            newTotalPrice -= newCartItems.get(itemId).getPrice();
            cart.setTotalPrice(newTotalPrice);

            newCartItems.remove(itemId);
            cart.setCartItems(newCartItems);

        } else {
            throw new CartItemNotFoundException(itemId);
        }
    }
    @Override
    public Cart buyCart() {
    //TODO Stripe
    cart.setBoughtAt(LocalDate.now());
    return updateCart(cart, cart.getId());
    }

    @Override
    public void deleteCart(UUID id) throws CartNotFoundException {
    //TODO delete cart from db
    }

    @Override
    public Order getCart(UUID id) throws CartNotFoundException {
        //TODO get cart from db
        return null;
    }

    @Override
    public List<Cart> getAllCarts() {
        //TODO get carts from db
        return null;
    }

    @Override
    public Cart updateCart(Cart cart, UUID id) throws CartNotFoundException {
        //TODO Update cart in DB
        return null;
    }

}
