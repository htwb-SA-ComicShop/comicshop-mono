package com.example.cart.port.user.exception;

import java.util.UUID;

public class CartNotFoundException extends RuntimeException {

    public CartNotFoundException(UUID id) {
      super("Could not find product " +id);
    }
}
