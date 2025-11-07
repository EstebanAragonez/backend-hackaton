package com.backend.hackaton.product.application.exception;

public class ProductNotOwnedException extends RuntimeException {
    public ProductNotOwnedException(String message) {
        super(message);
    }
}

