package com.example.cart.core.domain.service.impl;

import com.example.cart.core.domain.model.Cart;
import com.example.cart.core.domain.model.CartItem;
import com.example.cart.core.domain.service.interfaces.ICartRepository;
import com.example.cart.core.domain.service.interfaces.ICartService;
import com.example.cart.port.user.exception.CartItemNotFoundException;
import com.example.cart.port.user.exception.CartNotFoundException;
import com.example.cart.port.user.exception.NoPurchaseException;
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

    @Override
    public Cart createCart(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public void addToCart(CartItem item, UUID cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(()-> new CartNotFoundException(cartId));

        List<CartItem> cartItems = cart.getCartItems();
        if(!cartItems.contains(item)) {
            cartItems.add(item);
            cart.setCartItems(cartItems);
            cartRepository.save(cart);
        }
        //TODO exception if cart already contains the item?
    }

    @Override
    public void removeFromCart(UUID itemId, UUID cartId) throws CartItemNotFoundException {
        Cart cart = cartRepository.findById(cartId).orElseThrow(()-> new CartNotFoundException(cartId));
        List<CartItem> newCartItems = cart.getCartItems();
        newCartItems.removeIf(entry -> entry.getId()==itemId);
        cart.setCartItems(newCartItems);
        cartRepository.save(cart);
    }

    @Override
    public Cart buyCart(UUID id) {
        Cart boughtCart = cartRepository.findById(id).orElseThrow(() -> new CartNotFoundException(id));
        //TODO Stripe
        boughtCart.setBoughtAt(LocalDate.now());
        return cartRepository.save(boughtCart);
    }

    @Override
    public void deleteCart(UUID id) throws CartNotFoundException {
        cartRepository.findById(id).orElseThrow(() -> new CartNotFoundException(id));
        cartRepository.deleteById(id);
    }

    @Override
    public Cart getCart(UUID id) throws CartNotFoundException {
        System.out.println("IN CART SERVICE GET CART, ID: " + id);
        return cartRepository.findById(id).orElseThrow(() -> new CartNotFoundException(id));
    }

    @Override
    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    @Override
    public Cart updateCart(Cart cart, UUID id) throws CartNotFoundException {
        Cart cartToBeUpdated = cartRepository.findById(id).orElseThrow(() -> new CartNotFoundException(id));
        if (cartToBeUpdated.isBought()){ throw new NoPurchaseException(); }
        cartToBeUpdated.setUsername(cart.getUsername());
        cartToBeUpdated.setCartItems(cart.getCartItems());

        return cartRepository.save(cartToBeUpdated);
    }
}
