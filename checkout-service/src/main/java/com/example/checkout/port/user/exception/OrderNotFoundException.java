package com.example.checkout.port.user.exception;

import java.util.UUID;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(UUID id) {
      super("Could not find product " +id);
    }
}
