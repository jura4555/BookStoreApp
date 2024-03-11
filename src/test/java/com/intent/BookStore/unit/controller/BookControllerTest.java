package com.intent.BookStore.unit.controller;

import com.intent.BookStore.controller.BookController;
import com.intent.BookStore.dto.BookDTO;
import com.intent.BookStore.facade.impl.BookFacadeImpl;
import com.intent.BookStore.unit.util.TestBookDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.intent.BookStore.unit.util.TestBookDataUtil.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @Mock
    private BookFacadeImpl bookFacadeImpl;

    @InjectMocks
    private BookController bookController;


    @Test
    void getAllBooksTest() {
        BookDTO bookDTO1 = TestBookDataUtil.getBookDTO1();
        BookDTO bookDTO2 = TestBookDataUtil.getBookDTO2();
        BookDTO bookDTO3 = TestBookDataUtil.getBookDTO3();

        Pageable pageable = PageRequest.of(PAGE_NUMBER_DEFAULT, PAGE_SIZE, Sort.by("id").ascending());
        Page<BookDTO> books = new PageImpl<>(List.of(bookDTO1, bookDTO2, bookDTO3), pageable, 3);
        ResponseEntity<Page<BookDTO>> expectedResponseEntity = ResponseEntity.status(HttpStatus.OK).body(books);

        when(bookFacadeImpl.getAllBooks(PAGE_NUMBER, PAGE_SIZE)).thenReturn(books);

        ResponseEntity<Page<BookDTO>> resultResponseEntity = bookController.getAllBooks(PAGE_NUMBER, PAGE_SIZE);

        assertThat(resultResponseEntity.getBody(), is(expectedResponseEntity.getBody()));
        assertThat(resultResponseEntity.getStatusCode(), is(expectedResponseEntity.getStatusCode()));
        verify(bookFacadeImpl).getAllBooks(PAGE_NUMBER, PAGE_SIZE);
    }

    @Test
    void getBookByIdTest() {
        BookDTO bookDTO = TestBookDataUtil.getBookDTO1();
        ResponseEntity<BookDTO> expectedResponseEntity = ResponseEntity.status(HttpStatus.OK).body(bookDTO);

        when(bookFacadeImpl.getBookById(BOOK_ID_1)).thenReturn(bookDTO);

        ResponseEntity<BookDTO> resultResponseEntity = bookController.getBookById(BOOK_ID_1);

        assertThat(resultResponseEntity.getBody(), is(expectedResponseEntity.getBody()));
        assertThat(resultResponseEntity.getStatusCode(), is(expectedResponseEntity.getStatusCode()));
        verify(bookFacadeImpl).getBookById(BOOK_ID_1);
    }

    @Test
    void getBookByTitleTest() {
        BookDTO bookDTO = TestBookDataUtil.getBookDTO1();
        ResponseEntity<BookDTO> expectedResponseEntity = ResponseEntity.status(HttpStatus.OK).body(bookDTO);

        when(bookFacadeImpl.getBookByTitle(TITLE_1)).thenReturn(bookDTO);

        ResponseEntity<BookDTO> resultResponseEntity = bookController.getBookByTitle(TITLE_1);

        assertThat(resultResponseEntity.getBody(), is(expectedResponseEntity.getBody()));
        assertThat(resultResponseEntity.getStatusCode(), is(expectedResponseEntity.getStatusCode()));
        verify(bookFacadeImpl).getBookByTitle(TITLE_1);
    }

    @Test
    void createBookTest() {
        BookDTO bookDTO = TestBookDataUtil.getBookDTO1().setId(0L);
        BookDTO createdBookDTO = TestBookDataUtil.getBookDTO1();
        ResponseEntity<BookDTO> expectedResponseEntity = ResponseEntity.status(HttpStatus.CREATED).body(createdBookDTO);

        when(bookFacadeImpl.createBook(bookDTO)).thenReturn(createdBookDTO);

        ResponseEntity<BookDTO> resultResponseEntity = bookController.createBook(bookDTO);

        assertThat(resultResponseEntity.getBody(), is(expectedResponseEntity.getBody()));
        assertThat(resultResponseEntity.getStatusCode(), is(expectedResponseEntity.getStatusCode()));
        verify(bookFacadeImpl).createBook(bookDTO);
    }

    @Test
    void updateBookTest() {
        BookDTO bookDTO = TestBookDataUtil.getBookDTO1().setId(0L);
        BookDTO updatedBookDTO = TestBookDataUtil.getBookDTO1();
        ResponseEntity<BookDTO> expectedResponseEntity = ResponseEntity.status(HttpStatus.OK).body(updatedBookDTO);

        when(bookFacadeImpl.updateBook(BOOK_ID_1, bookDTO)).thenReturn(updatedBookDTO);

        ResponseEntity<BookDTO> resultResponseEntity = bookController.updateBook(BOOK_ID_1, bookDTO);

        assertThat(resultResponseEntity.getBody(), is(expectedResponseEntity.getBody()));
        assertThat(resultResponseEntity.getStatusCode(), is(expectedResponseEntity.getStatusCode()));
        verify(bookFacadeImpl).updateBook(BOOK_ID_1, bookDTO);
    }

    @Test
    void deleteBookTest() {
        ResponseEntity<Void> resultResponseEntity = bookController.deleteBook(BOOK_ID_1);

        assertThat(resultResponseEntity.getStatusCode(), is(HttpStatus.NO_CONTENT));
        verify(bookFacadeImpl, times(1)).deleteBook(BOOK_ID_1);
    }

    @Test
    void getAllBooksByCriteriaTest() {
        BookDTO bookDTO1 = TestBookDataUtil.getBookDTO1();
        BookDTO bookDTO3 = TestBookDataUtil.getBookDTO3();
        Pageable pageable = PageRequest.of(PAGE_NUMBER_DEFAULT, PAGE_SIZE_FOT_CRITERIA, Sort.by("id").ascending());
        Page<BookDTO> books = new PageImpl<>(List.of(bookDTO1, bookDTO3), pageable, 2);
        ResponseEntity<Page<BookDTO>> expectedResponseEntity = ResponseEntity.status(HttpStatus.OK).body(books);

        when(bookFacadeImpl.getAllBooksByCriteria(AUTHOR_NAME, GENRE, MIN_PRICE, MAX_PRICE, QUANTITY, PAGE_NUMBER, PAGE_SIZE_FOT_CRITERIA)).thenReturn(books);

        ResponseEntity<Page<BookDTO>> resultResponseEntity = bookController.getAllBooksByCriteria(AUTHOR_NAME, GENRE, MIN_PRICE, MAX_PRICE, QUANTITY, PAGE_NUMBER, PAGE_SIZE_FOT_CRITERIA);

        assertThat(resultResponseEntity.getBody(), is(expectedResponseEntity.getBody()));
        assertThat(resultResponseEntity.getStatusCode(), is(expectedResponseEntity.getStatusCode()));
        verify(bookFacadeImpl).getAllBooksByCriteria(AUTHOR_NAME, GENRE, MIN_PRICE, MAX_PRICE, QUANTITY, PAGE_NUMBER, PAGE_SIZE_FOT_CRITERIA);
    }

}
