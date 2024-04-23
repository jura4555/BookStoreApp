package com.intent.BookStore.util;

public class ExceptionMessageUtil {
    public static final String BOOK_NOT_FOUND_BY_ID_ERROR_MESSAGE = "Book with id: %d is not found";
    public static final String BOOK_NOT_FOUND_BY_TITLE_ERROR_MESSAGE = "Book with title: %s is not found";
    public static final String USER_NOT_FOUND_BY_ID_ERROR_MESSAGE = "User with id: %d is not found";
    public static final String USER_NOT_FOUND_BY_USERNAME_ERROR_MESSAGE = "User with username: %s is not found";
    public static final String PASSWORD_MISMATCH_ERROR_MESSAGE = "New password and confirm password do not match";
    public static final String INCORRECT_PASSWORD_ERROR_MESSAGE = "Existing password and current password do not match";
    public static final String ORDER_NOT_FOUND_BY_ID_ERROR_MESSAGE = "Order with id: %d is not found";
    public static final String INSUFFICIENT_STOCK_ERROR_MESSAGE = "Insufficient stock for book with ID: %d. Available quantity: %d, requested quantity: %d.";
    public static final String ORDER_ITEM_NOT_FOUND_BY_ID_ERROR_MESSAGE = "OrderItem with id: %d is not found";
    public static final String ORDER_CLOSED_ERROR_MESSAGE = "Cannot perform operation because order with id: %d is closed";
    public static final String INSUFFICIENT_FUNDS_ERROR_MESSAGE = "There is not enough money in the account to complete the purchase";
    public static final String INVALID_JWT_AUTHENTICATION_EXCEPTION = "Invalid token: %s";
    public static final String AUTHENTICATION_EXCEPTION = "Incorrect username or password!!!";
    public static final String ROLE_CHANGE_FORBIDDEN_EXCEPTION = "Changing user role to ADMIN is not allowed";


}
