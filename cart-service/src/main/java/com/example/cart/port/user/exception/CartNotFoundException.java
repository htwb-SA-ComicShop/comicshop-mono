package com.example.cart.port.user.exception;

public class CartNotFoundException extends RuntimeException {

    public CartNotFoundException(String id) {
      super("Could not find cart of " +id);
    }
}
