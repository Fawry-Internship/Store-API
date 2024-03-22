package com.example.trying.exception;

public class StockNotFoundException extends RuntimeException {

    public StockNotFoundException(String message) {
        super(message);
    }
}