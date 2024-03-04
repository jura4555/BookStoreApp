package com.intent.BookStore.controller;

import com.intent.BookStore.dto.BookDTO;
import com.intent.BookStore.facade.BookFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookFacade bookFacade;

    @GetMapping("/books")
    public ResponseEntity<Page<BookDTO>> getAllBooks(
            @RequestParam(defaultValue = "1") @Min(value = 1, message = "page should be greater or equals one")
            int pageNum,
            @RequestParam(defaultValue = "1") @Min(value = 1, message = "page size should be greater or equals one")
            int pageSize) {
        Page<BookDTO> books = bookFacade.getAllBooks(pageNum, pageSize);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        BookDTO book = bookFacade.getBookById(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @GetMapping("/books/title/{title}")
    public ResponseEntity<BookDTO> getBookByTitle(@PathVariable String title) {
        BookDTO book = bookFacade.getBookByTitle(title);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @PostMapping("/books")
    public ResponseEntity<BookDTO> createBook(@Valid @RequestBody BookDTO bookDTO) {
        BookDTO createdBook = bookFacade.createBook(bookDTO);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @Valid @RequestBody BookDTO updatedBookDTO) {
        BookDTO updatedBook = bookFacade.updateBook(id, updatedBookDTO);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookFacade.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/books/search")
    public ResponseEntity<Page<BookDTO>> getAllBooksByCriteria(
            @RequestParam(required = false, defaultValue = "") String authorName,
            @RequestParam(required = false, defaultValue = "") String genre,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false, defaultValue = "0") int quantity,
            @RequestParam(defaultValue = "1")
            @Min(value = 1, message = "page should be greater or equals one") int pageNum,
            @RequestParam(defaultValue = "1")
            @Min(value = 1, message = "page size should be greater or equals one") int pageSize) {
        Page<BookDTO> books = bookFacade.getAllBooksByCriteria(authorName, genre, minPrice, maxPrice, quantity, pageNum, pageSize);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
}
