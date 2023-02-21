package com.example.tdd.bookstore.infra.exceptions;

public class ValidationsException extends RuntimeException {
    public ValidationsException(String message) {
        super(message);
    }
}
