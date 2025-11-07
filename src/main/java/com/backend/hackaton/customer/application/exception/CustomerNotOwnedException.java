package com.backend.hackaton.customer.application.exception;

public class CustomerNotOwnedException extends RuntimeException {
    public CustomerNotOwnedException(String message) {
        super(message);
    }
}

