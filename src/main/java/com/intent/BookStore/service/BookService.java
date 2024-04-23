package com.intent.BookStore.service;

import com.intent.BookStore.model.Book;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface BookService {
    Page<Book> getAllBooks(int pageNum, int pageSize);

    Book getBookById(Long id);

    Book getBookByTitle(String title);

    Book createBook(Book book);

    Book updateBook(Long id, Book updatedBook);

    Page<Book> getAllBooksByCriteria(String authorName, String genre, BigDecimal minPrice, BigDecimal maxPrice, int quantity, int pageNum, int pageSize);

}
