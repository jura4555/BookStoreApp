package com.intent.BookStore.exception;

public class BookNotFoundException extends NotFoundException{

    public BookNotFoundException(String message) {
        super(message);
    }
}
