package com.intent.BookStore.aspect;

import com.intent.BookStore.model.Book;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class BookAspect {

    @AfterReturning(pointcut = "execution(* com.intent.BookStore.service.impl.BookServiceImpl.getAllBooks(..))", returning = "result")
    public void logGetAllBooks(JoinPoint joinPoint, Page<Book> result) {
        log.info("getAllBooks() method called with parameters: {}", Arrays.toString(joinPoint.getArgs()));
        log.info("Retrieved {} books", result.getNumberOfElements());
    }

    @AfterReturning(pointcut = "execution(* com.intent.BookStore.service.impl.BookServiceImpl.getBookById(..))", returning = "book")
    public void logGetBookById(JoinPoint joinPoint, Book book) {
        Long id = (Long) joinPoint.getArgs()[0];
        log.info("getBookById() method called with id: {}", id);
        log.info("Book with id: {} was found", book.getId());
    }

    @AfterReturning(pointcut = "execution(* com.intent.BookStore.service.impl.BookServiceImpl.getBookByTitle(..))", returning = "book")
    public void logGetBookByTitle(JoinPoint joinPoint, Book book) {
        String title = (String) joinPoint.getArgs()[0];
        log.info("getBookByTitle() method called with title: {}", title);
        log.info("Book with title: {} was found", book.getTitle());
    }

    @AfterReturning(pointcut = "execution(* com.intent.BookStore.service.impl.BookServiceImpl.createBook(..))", returning = "book")
    public void logCreateBook(JoinPoint joinPoint, Book book) {
        log.info("Book with id : {} was created", book.getId());
    }

    @AfterReturning(pointcut = "execution(* com.intent.BookStore.service.impl.BookServiceImpl.updateBook(..))", returning = "book")
    public void logUpdateBook(JoinPoint joinPoint, Book book) {
        Long id = (Long) joinPoint.getArgs()[0];
        log.info("Book with id : {} was updated", book.getId());
    }

    @AfterReturning("execution(* com.intent.BookStore.service.impl.BookServiceImpl.deleteBook(..)) && args(id)")
    public void logDeleteBook(JoinPoint joinPoint, Long id) {
        log.info("deleteBook() method called with id: {}", id);
    }

    @AfterReturning(pointcut = "execution(* com.intent.BookStore.service.impl.BookServiceImpl.getAllBooksByCriteria(..))", returning = "result")
    public void logGetAllBooksByCriteria(JoinPoint joinPoint, Page<Book> result) {
        log.info("getAllBooksByCriteria() method called with parameters: {}", Arrays.toString(joinPoint.getArgs()));
        log.info("Retrieved {} books", result.getTotalElements());
    }
    
}
