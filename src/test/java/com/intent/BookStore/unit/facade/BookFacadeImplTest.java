package com.intent.BookStore.unit.facade;

import com.intent.BookStore.dto.BookDTO;
import com.intent.BookStore.facade.impl.BookFacadeImpl;
import com.intent.BookStore.model.Book;
import com.intent.BookStore.service.impl.BookServiceImpl;
import com.intent.BookStore.unit.util.TestBookDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;

import static com.intent.BookStore.unit.util.TestBookDataUtil.*;
import static com.intent.BookStore.unit.util.TestUserDataUtil.USER_ID_1;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
class BookFacadeImplTest {

    @Mock
    private BookServiceImpl bookServiceImpl;

    @InjectMocks
    private BookFacadeImpl bookFacade;



    @Test
    void getAllBookTest() {
        Book book1 = TestBookDataUtil.getBook1();
        Book book2 = TestBookDataUtil.getBook2();
        Book book3 = TestBookDataUtil.getBook3();

        BookDTO bookDTO1 = TestBookDataUtil.getBookDTO1();
        BookDTO bookDTO2 = TestBookDataUtil.getBookDTO2();
        BookDTO bookDTO3 = TestBookDataUtil.getBookDTO3();

        Pageable pageable = PageRequest.of(PAGE_NUMBER_DEFAULT, PAGE_SIZE, Sort.by("id").ascending());
        Page<Book> books = new PageImpl<>(List.of(book1, book2, book3), pageable, 3);

        when(bookServiceImpl.getAllBooks(PAGE_NUMBER, PAGE_SIZE)).thenReturn(books);
        Page<BookDTO> result = bookFacade.getAllBooks(PAGE_NUMBER, PAGE_SIZE);
        assertThat(result.getContent().size(), is(books.getNumberOfElements()));
        assertThat(result, contains(bookDTO1, bookDTO2, bookDTO3));
        verify(bookServiceImpl, times(1)).getAllBooks(PAGE_NUMBER, PAGE_SIZE);
    }

    @Test
    void getBookByIdTest() {
        Book book = TestBookDataUtil.getBook1();
        BookDTO expectedBookDTO = TestBookDataUtil.getBookDTO1();
        when(bookServiceImpl.getBookById(BOOK_ID_1)).thenReturn(book);
        BookDTO result = bookFacade.getBookById(USER_ID_1);
        assertThat(result, allOf(
                hasProperty("id", equalTo(expectedBookDTO.getId())),
                hasProperty("title", equalTo(expectedBookDTO.getTitle())),
                hasProperty("authorName", equalTo(expectedBookDTO.getAuthorName())),
                hasProperty("genre", equalTo(expectedBookDTO.getGenre())),
                hasProperty("price", equalTo(expectedBookDTO.getPrice())),
                hasProperty("quantity", equalTo(expectedBookDTO.getQuantity())),
                hasProperty("description", equalTo(expectedBookDTO.getDescription())),
                hasProperty("imageURL", equalTo(expectedBookDTO.getImageURL()))
        ));
        verify(bookServiceImpl, times(1)).getBookById(BOOK_ID_1);
    }

    @Test
    void getBookByTitleTest() {
        Book book = TestBookDataUtil.getBook1();
        BookDTO expectedBookDTO = TestBookDataUtil.getBookDTO1();
        when(bookServiceImpl.getBookByTitle(TITLE_1)).thenReturn(book);
        BookDTO result = bookFacade.getBookByTitle(TITLE_1);
        assertThat(result, allOf(
                hasProperty("id", equalTo(expectedBookDTO.getId())),
                hasProperty("title", equalTo(expectedBookDTO.getTitle())),
                hasProperty("authorName", equalTo(expectedBookDTO.getAuthorName())),
                hasProperty("genre", equalTo(expectedBookDTO.getGenre())),
                hasProperty("price", equalTo(expectedBookDTO.getPrice())),
                hasProperty("quantity", equalTo(expectedBookDTO.getQuantity())),
                hasProperty("description", equalTo(expectedBookDTO.getDescription())),
                hasProperty("imageURL", equalTo(expectedBookDTO.getImageURL()))
        ));
        verify(bookServiceImpl, times(1)).getBookByTitle(TITLE_1);
    }

    @Test
    void createBookTest() {
        Book createdBook = TestBookDataUtil.getBook1();
        BookDTO bookDTO = TestBookDataUtil.getBookDTO1().setId(0L);
        when(bookServiceImpl.createBook(any(Book.class))).thenReturn(createdBook);
        BookDTO result = bookFacade.createBook(bookDTO);
        assertThat(result, allOf(
                hasProperty("id", equalTo(BOOK_ID_1)),
                hasProperty("title", equalTo(bookDTO.getTitle())),
                hasProperty("authorName", equalTo(bookDTO.getAuthorName())),
                hasProperty("genre", equalTo(bookDTO.getGenre())),
                hasProperty("price", equalTo(bookDTO.getPrice())),
                hasProperty("quantity", equalTo(bookDTO.getQuantity())),
                hasProperty("description", equalTo(bookDTO.getDescription())),
                hasProperty("imageURL", equalTo(bookDTO.getImageURL()))
        ));
        verify(bookServiceImpl, times(1)).createBook(any(Book.class));
    }

    @Test
    void updateBookTest() {
        Book updatedBook = TestBookDataUtil.getBook1();
        BookDTO bookDTO = TestBookDataUtil.getBookDTO1().setId(0L);
        when(bookServiceImpl.updateBook(eq(BOOK_ID_1), any(Book.class))).thenReturn(updatedBook);
        BookDTO result = bookFacade.updateBook(BOOK_ID_1, bookDTO);
        assertThat(result, allOf(
                hasProperty("id", equalTo(BOOK_ID_1)),
                hasProperty("title", equalTo(bookDTO.getTitle())),
                hasProperty("authorName", equalTo(bookDTO.getAuthorName())),
                hasProperty("genre", equalTo(bookDTO.getGenre())),
                hasProperty("price", equalTo(bookDTO.getPrice())),
                hasProperty("quantity", equalTo(bookDTO.getQuantity())),
                hasProperty("description", equalTo(bookDTO.getDescription())),
                hasProperty("imageURL", equalTo(bookDTO.getImageURL()))
        ));
        verify(bookServiceImpl, times(1)).updateBook(eq(BOOK_ID_1), any(Book.class));
    }

    @Test
    void getAllBooksByCriteriaTest() {
        Book book1 = TestBookDataUtil.getBook1();
        Book book3 = TestBookDataUtil.getBook3();
        Pageable pageable = PageRequest.of(PAGE_NUMBER_DEFAULT, PAGE_SIZE_FOT_CRITERIA, Sort.by("id").ascending());
        Page<Book> books = new PageImpl<>(List.of(book1, book3), pageable, 2);

        BookDTO bookDTO1 = TestBookDataUtil.getBookDTO1();
        BookDTO bookDTO3 = TestBookDataUtil.getBookDTO3();

        when(bookServiceImpl.getAllBooksByCriteria(AUTHOR_NAME, GENRE, MIN_PRICE, MAX_PRICE, QUANTITY, PAGE_NUMBER, PAGE_SIZE_FOT_CRITERIA)).thenReturn(books);
        Page<BookDTO> result = bookFacade.getAllBooksByCriteria(AUTHOR_NAME, GENRE, MIN_PRICE, MAX_PRICE, QUANTITY, PAGE_NUMBER, PAGE_SIZE_FOT_CRITERIA);

        assertThat(result.getTotalElements(), is((long) books.getNumberOfElements()));
        assertThat(result, contains(bookDTO1, bookDTO3));
        verify(bookServiceImpl, times(1)).getAllBooksByCriteria(AUTHOR_NAME, GENRE, MIN_PRICE, MAX_PRICE, QUANTITY, PAGE_NUMBER, PAGE_SIZE_FOT_CRITERIA);
    }

}
