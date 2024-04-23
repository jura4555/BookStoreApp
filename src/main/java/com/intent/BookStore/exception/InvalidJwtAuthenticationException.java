package com.intent.BookStore.exception;

public class InvalidJwtAuthenticationException extends TypeException {
    public InvalidJwtAuthenticationException(String message) {
        super(message);
    }
}
