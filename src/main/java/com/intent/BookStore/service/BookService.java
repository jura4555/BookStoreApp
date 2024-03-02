package com.intent.BookStore.service;

import com.intent.BookStore.model.Book;

import java.math.BigDecimal;
import java.util.List;

public interface BookService {
    List<Book> getAllBooks();

    Book getBookById(Long id);

    Book getBookByTitle(String title);

    Book createBook(Book book);

    Book updateBook(Long id, Book updatedBook);

    void deleteBook(Long id);

    List<Book> getAllBooksByCriteria(String authorName, String genre, BigDecimal minPrice, BigDecimal maxPrice, int quantity);

}
