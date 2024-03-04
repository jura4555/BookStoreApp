package com.intent.BookStore.facade;

import com.intent.BookStore.dto.BookDTO;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface BookFacade {
    Page<BookDTO> getAllBooks(int pageNum, int pageSize);

    BookDTO getBookById(Long id);

    BookDTO getBookByTitle(String title);

    BookDTO createBook(BookDTO bookDTO);

    BookDTO updateBook(Long id, BookDTO updatedBookDTO);

    void deleteBook(Long id);

    Page<BookDTO> getAllBooksByCriteria(String authorName, String genre, BigDecimal minPrice, BigDecimal maxPrice, int quantity, int pageNum, int pageSize);

}
