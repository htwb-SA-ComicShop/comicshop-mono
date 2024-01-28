package com.example.product.port.user.exception;

import java.util.UUID;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(UUID id) {
      super("Could not find product " +id);
    }
}
