package com.intent.BookStore.facade.impl;

import com.intent.BookStore.dto.BookDTO;
import com.intent.BookStore.facade.BookFacade;
import com.intent.BookStore.mapper.BookMapperUtil;
import com.intent.BookStore.model.Book;
import com.intent.BookStore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static com.intent.BookStore.mapper.BookMapperUtil.toBook;
import static com.intent.BookStore.mapper.BookMapperUtil.toBookDto;

@Component
@RequiredArgsConstructor
public class BookFacadeImpl implements BookFacade {

    private final BookService bookService;
    @Override
    public Page<BookDTO> getAllBooks(int pageNum, int pageSize) {
        Page<Book> bookPage = bookService.getAllBooks(pageNum, pageSize);
        return bookPage.map(BookMapperUtil::toBookDto);
    }

    @Override
    public BookDTO getBookById(Long id) {
        Book book = bookService.getBookById(id);
        return toBookDto(book);
    }

    @Override
    public BookDTO getBookByTitle(String title){
        Book book = bookService.getBookByTitle(title);
        return toBookDto(book);
    }

    @Override
    public BookDTO createBook(BookDTO bookDTO) {
        Book createdBook = bookService.createBook(toBook(bookDTO));
        return toBookDto(createdBook);
    }

    @Override
    public BookDTO updateBook(Long id, BookDTO updatedBootDTO) {
        Book updatedBook = bookService.updateBook(id, toBook(updatedBootDTO));
        return toBookDto(updatedBook);
    }


    @Override
    public Page<BookDTO> getAllBooksByCriteria(String authorName, String genre, BigDecimal minPrice, BigDecimal maxPrice, int quantity, int pageNum, int pageSize) {
        Page<Book> bookPage = bookService.getAllBooksByCriteria(authorName, genre, minPrice, maxPrice, quantity, pageNum, pageSize);
        return bookPage.map(BookMapperUtil::toBookDto);
    }

}
