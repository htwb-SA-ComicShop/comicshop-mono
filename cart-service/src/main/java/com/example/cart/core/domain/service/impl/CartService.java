package com.example.cart.core.domain.service.impl;

import com.example.cart.core.domain.model.Cart;
import com.example.cart.core.domain.model.CartItem;
import com.example.cart.core.domain.service.interfaces.ICartRepository;
import com.example.cart.core.domain.service.interfaces.ICartService;
import com.example.cart.port.user.exception.CartItemNotFoundException;
import com.example.cart.port.user.exception.CartNotFoundException;
import com.example.cart.port.user.exception.NoPurchaseException;
import com.stripe.exception.StripeException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
        if(cartItems.stream().noneMatch(cartItem -> cartItem.getProductId()==item.getProductId())) {
            cartItems.add(item);
            cart.setCartItems(cartItems);
            System.out.println("CART ITEMS ARE: " + cart.getCartItems().stream().collect(Collectors.toList()));
            cartRepository.save(cart);
        }
        //TODO exception if cart already contains the item?
    }

    @Override
    public void removeFromCart(UUID itemId, UUID cartId) throws CartItemNotFoundException {
        Cart cart = cartRepository.findById(cartId).orElseThrow(()-> new CartNotFoundException(cartId));
        List<CartItem> newCartItems = cart.getCartItems();
        newCartItems.removeIf(entry -> entry.getId().equals(itemId));
        cart.setCartItems(newCartItems);

        cartRepository.save(cart);
    }

    @Override
    public Cart checkoutCart(UUID id) throws StripeException {
        Cart boughtCart = cartRepository.findById(id).orElseThrow(() -> new CartNotFoundException(id));
        boughtCart.setBoughtAt(LocalDate.now());

        boughtCart.setLinkToInvoice(
                stripeService.getLinkToFile(
                        boughtCart.generateInvoice()).toString());

        StringBuilder invoiceBuilder = new StringBuilder();
        for (CartItem item :
                boughtCart.getCartItems()) {
            invoiceBuilder.append(item.getName()).append(": ")
                    .append(stripeService.getLinkToFile(
                            boughtCart.getPathToComic(item.getName())).toString()).append("\n");
        }
        boughtCart.setLinkToContent(invoiceBuilder.toString());

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
