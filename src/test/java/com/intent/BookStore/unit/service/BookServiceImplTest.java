package com.intent.BookStore.unit.service;

import com.intent.BookStore.exception.BookNotFoundException;
import com.intent.BookStore.model.Book;
import com.intent.BookStore.repository.BookRepository;
import com.intent.BookStore.service.impl.BookServiceImpl;
import com.intent.BookStore.unit.util.TestBookDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.intent.BookStore.unit.util.TestBookDataUtil.BOOK_ID_1;
import static com.intent.BookStore.unit.util.TestBookDataUtil.TITLE_1;
import static com.intent.BookStore.unit.util.TestUserDataUtil.USER_ID_1;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private static final BigDecimal PRICE_UPDATE = BigDecimal.valueOf(15.00);
    private static final int QUANTITY_UPDATE = 40;

    @Test
    void getAllBookTest() {
        Book book1 = TestBookDataUtil.getBook1();
        Book book2 = TestBookDataUtil.getBook2();
        Book book3 = TestBookDataUtil.getBook3();

        Pageable pageable = PageRequest.of(0, 3, Sort.by("id").ascending());
        Page<Book> books = new PageImpl<>(List.of(book1, book2, book3), pageable, 3);

        when(bookRepository.findAll(pageable)).thenReturn(books);

        Page<Book> result = bookService.getAllBooks(1, 3);
        assertThat(result.getTotalElements(), is((long) books.getNumberOfElements()));
        assertThat(result, contains(book1, book2, book3));
        verify(bookRepository, times(1)).findAll(pageable);
    }

    @Test
    void getBookByIdTest() {
        Book book = TestBookDataUtil.getBook1();
        when(bookRepository.findById(BOOK_ID_1)).thenReturn(Optional.of(book));
        Book result = bookService.getBookById(USER_ID_1);
        assertThat(result, allOf(
                hasProperty("id", equalTo(book.getId())),
                hasProperty("title", equalTo(book.getTitle())),
                hasProperty("authorName", equalTo(book.getAuthorName())),
                hasProperty("genre", equalTo(book.getGenre())),
                hasProperty("price", equalTo(book.getPrice())),
                hasProperty("quantity", equalTo(book.getQuantity())),
                hasProperty("description", equalTo(book.getDescription())),
                hasProperty("imageURL", equalTo(book.getImageURL()))
        ));
        verify(bookRepository, times(1)).findById(BOOK_ID_1);
    }

    @Test
    void getBookByIdWithNotFoundExceptionTest(){
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(BookNotFoundException.class, () -> bookService.getBookById(anyLong()));
    }

    @Test
    void getBookByTitleTest() {
        Book book = TestBookDataUtil.getBook1();
        when(bookRepository.findByTitle(TITLE_1)).thenReturn(Optional.of(book));
        Book result = bookService.getBookByTitle(TITLE_1);
        assertThat(result, allOf(
                hasProperty("id", equalTo(book.getId())),
                hasProperty("title", equalTo(book.getTitle())),
                hasProperty("authorName", equalTo(book.getAuthorName())),
                hasProperty("genre", equalTo(book.getGenre())),
                hasProperty("price", equalTo(book.getPrice())),
                hasProperty("quantity", equalTo(book.getQuantity())),
                hasProperty("description", equalTo(book.getDescription())),
                hasProperty("imageURL", equalTo(book.getImageURL()))
        ));
        verify(bookRepository, times(1)).findByTitle(TITLE_1);
    }

    @Test
    void getBookByTitleWithNotFoundExceptionTest(){
        when(bookRepository.findByTitle(anyString())).thenReturn(Optional.empty());
        assertThrows(BookNotFoundException.class, () -> bookService.getBookByTitle(anyString()));
    }

    @Test
    void createBookTest() {
        Book book  = TestBookDataUtil.getBook1().setId(0L);
        Book createdBook = TestBookDataUtil.getBook1();
        when(bookRepository.save(book)).thenReturn(createdBook);
        Book result = bookService.createBook(book);
        assertThat(result, allOf(
                hasProperty("id", equalTo(createdBook.getId())),
                hasProperty("title", equalTo(createdBook.getTitle())),
                hasProperty("authorName", equalTo(createdBook.getAuthorName())),
                hasProperty("genre", equalTo(createdBook.getGenre())),
                hasProperty("price", equalTo(createdBook.getPrice())),
                hasProperty("quantity", equalTo(createdBook.getQuantity())),
                hasProperty("description", equalTo(createdBook.getDescription())),
                hasProperty("imageURL", equalTo(createdBook.getImageURL()))
        ));
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void updateBookTest() {
        Book updatedBook = TestBookDataUtil.getBook1()
                .setPrice(PRICE_UPDATE)
                .setQuantity(QUANTITY_UPDATE);
        when(bookRepository.existsById(BOOK_ID_1)).thenReturn(true);
        when(bookRepository.save(updatedBook)).thenReturn(updatedBook);
        Book result = bookService.updateBook(BOOK_ID_1, updatedBook);
        assertThat(result, allOf(
                hasProperty("id", equalTo(updatedBook.getId())),
                hasProperty("title", equalTo(updatedBook.getTitle())),
                hasProperty("authorName", equalTo(updatedBook.getAuthorName())),
                hasProperty("genre", equalTo(updatedBook.getGenre())),
                hasProperty("price", equalTo(updatedBook.getPrice())),
                hasProperty("quantity", equalTo(updatedBook.getQuantity())),
                hasProperty("description", equalTo(updatedBook.getDescription())),
                hasProperty("imageURL", equalTo(updatedBook.getImageURL()))
        ));
        verify(bookRepository, times(1)).existsById(BOOK_ID_1);
        verify(bookRepository, times(1)).save(updatedBook);
    }

    @Test
    void deleteBookTest() {
        when(bookRepository.existsById(BOOK_ID_1)).thenReturn(true);
        bookService.deleteBook(BOOK_ID_1);
        verify(bookRepository, times(1)).existsById(BOOK_ID_1);
        verify(bookRepository, times(1)).deleteById(BOOK_ID_1);
    }

    @Test
    void deleteBookWithNotFoundExceptionTest(){
        when(bookRepository.existsById(anyLong())).thenReturn(false);
        assertThrows(BookNotFoundException.class, () -> bookService.deleteBook(anyLong()));
    }

    @Test
    void getAllBooksByCriteriaTest() {
        Book book1 = TestBookDataUtil.getBook1();
        Book book3 = TestBookDataUtil.getBook3();
        Pageable pageable = PageRequest.of(0, 2, Sort.by("id").ascending());

        Page<Book> books = new PageImpl<>(List.of(book1, book3), pageable, 2);
        when(bookRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(books);

        Page<Book> result = bookService.getAllBooksByCriteria("Harper Lee", "Fiction", null, null, 10, 1, 10);
        assertThat(result.getTotalElements(), is((long) books.getNumberOfElements()));
        assertThat(result, contains(book1, book3));
        verify(bookRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
    }
}
