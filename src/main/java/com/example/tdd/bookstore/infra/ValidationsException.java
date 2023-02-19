package com.example.tdd.bookstore.infra;

public class ValidationsException extends RuntimeException {
    public ValidationsException(String message) {
        super(message);
    }
}
