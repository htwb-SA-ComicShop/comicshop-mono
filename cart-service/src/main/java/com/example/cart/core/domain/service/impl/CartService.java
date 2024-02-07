package com.example.cart.core.domain.service.impl;

import com.example.cart.core.domain.model.Cart;
import com.example.cart.core.domain.model.CartItem;
import com.example.cart.core.domain.service.interfaces.ICartRepository;
import com.example.cart.core.domain.service.interfaces.ICartService;
import com.example.cart.port.user.exception.CartNotFoundException;
import com.stripe.exception.StripeException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class CartService implements ICartService {
    @Autowired
    private final ICartRepository cartRepository;

    StripeService stripeService;

    @Override
    public Cart createCart(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public void addToCart(CartItem item, UUID cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new CartNotFoundException(cartId));

        List<CartItem> cartItems = cart.getCartItems();
        if (cartItems.stream().noneMatch(cartItem -> cartItem.getProductId() == item.getProductId())) {
            cartItems.add(item);
            cart.setCartItems(cartItems);
            cartRepository.save(cart);
        }
    }

    @Override
    public void removeFromCart(UUID itemId, UUID cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new CartNotFoundException(cartId));
        List<CartItem> newCartItems = cart.getCartItems();
        newCartItems.removeIf(entry -> entry.getId().equals(itemId));
        cart.setCartItems(newCartItems);
        cartRepository.save(cart);
    }

    @Override
    public Cart checkoutCart(UUID id) throws StripeException {
        Cart boughtCart = cartRepository.findById(id).orElseThrow(() -> new CartNotFoundException(id));
        boughtCart.setBoughtAt(LocalDate.now());

        boughtCart.setLinkToInvoice(" LinkToInvoice ");

        StringBuilder contentBuilder = new StringBuilder();
        for (CartItem item : boughtCart.getCartItems()) {
            contentBuilder.append("LinkTo ").append(item.getName()).append(":\n ");
        }
        boughtCart.setLinkToContent(contentBuilder.toString());
        return cartRepository.save(boughtCart);
    }

    @Override
    public void deleteCart(UUID id) throws CartNotFoundException {
        cartRepository.findById(id).orElseThrow(() -> new CartNotFoundException(id));
        cartRepository.deleteById(id);
    }

    @Override
    public Cart getCart(UUID id) throws CartNotFoundException {
        return cartRepository.findById(id).orElseThrow(() -> new CartNotFoundException(id));
    }

    @Override
    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    @Override
    public Cart updateCart(Cart cart, UUID id) throws CartNotFoundException {
        Cart cartToBeUpdated = cartRepository.findById(id).orElseThrow(() -> new CartNotFoundException(id));
        cartToBeUpdated.setUsername(cart.getUsername());
        cartToBeUpdated.setCartItems(cart.getCartItems());
        cartToBeUpdated.setEmail(cart.getEmail());

        return cartRepository.save(cartToBeUpdated);
    }

}
