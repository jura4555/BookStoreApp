package com.intent.BookStore.facade;

import com.intent.BookStore.dto.BookDTO;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

public interface BookFacade {
    List<BookDTO> getAllBooks();

    BookDTO getBookById(Long id);

    BookDTO getBookByTitle(String title);

    BookDTO createBook(BookDTO bookDTO);

    BookDTO updateBook(Long id, BookDTO updatedBookDTO);

    void deleteBook(Long id);

    List<BookDTO> getAllBooksByCriteria(String authorName, String genre, BigDecimal minPrice, BigDecimal maxPrice, int quantity);

}
