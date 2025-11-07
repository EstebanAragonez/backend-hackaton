package com.backend.hackaton.fixedexpense.application.exception;

public class FixedExpenseNotOwnedException extends RuntimeException {
    public FixedExpenseNotOwnedException(String message) {
        super(message);
    }
}

