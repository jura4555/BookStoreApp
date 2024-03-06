package com.intent.BookStore.exception;

import java.time.LocalDateTime;

public record CustomError(
    String path,
    String message,
    int statusCode,
    LocalDateTime localDateTime
) {}
