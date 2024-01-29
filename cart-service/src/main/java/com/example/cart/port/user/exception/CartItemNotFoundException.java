package com.example.cart.port.user.exception;

import java.util.UUID;

public class CartItemNotFoundException extends RuntimeException {

    public CartItemNotFoundException(UUID id) {
      super("Could not find product " +id);
    }
}
