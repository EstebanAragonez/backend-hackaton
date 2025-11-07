package com.backend.hackaton.fixedexpense.application.exception;

public class FixedExpenseNotFoundException extends RuntimeException {
    public FixedExpenseNotFoundException(String message) {
        super(message);
    }
}

