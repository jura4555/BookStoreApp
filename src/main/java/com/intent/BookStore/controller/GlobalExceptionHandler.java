package com.intent.BookStore.controller;

import com.intent.BookStore.exception.CustomError;
import com.intent.BookStore.exception.TypeException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<CustomError> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest req) {
        return ex.getBindingResult().getAllErrors().stream()
                .map(e -> new CustomError(req.getRequestURI(), e.getDefaultMessage(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now()))
                .collect(Collectors.toList());
    }

    @ExceptionHandler(TypeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomError handleServiceException(TypeException ex, HttpServletRequest req) {
        return new CustomError(req.getRequestURI(), ex.getMessage(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomError handleDataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest req) {
        return new CustomError(req.getRequestURI(), ex.getMessage(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CustomError handleException(Exception ex, HttpServletRequest req) {
        return new CustomError(req.getRequestURI(), ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now());
    }
}
