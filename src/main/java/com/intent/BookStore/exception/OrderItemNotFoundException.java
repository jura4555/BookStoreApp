package com.intent.BookStore.exception;

public class OrderItemNotFoundException extends NotFoundException{
    public OrderItemNotFoundException(String message) {
        super(message);
    }
}
