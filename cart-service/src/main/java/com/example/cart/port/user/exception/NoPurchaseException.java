package com.example.cart.port.user.exception;


public class NoPurchaseException extends RuntimeException {

    public NoPurchaseException() {
        super(" The cart was not bought yet ");
    }
}
