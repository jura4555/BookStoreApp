package com.intent.BookStore.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intent.BookStore.dto.BookDTO;
import com.intent.BookStore.facade.impl.BookFacadeImpl;
import com.intent.BookStore.integration.AbstractTestContainer;
import com.intent.BookStore.model.User;
import com.intent.BookStore.repository.BookRepository;
import com.intent.BookStore.repository.UserRepository;
import com.intent.BookStore.security.jwt.JwtTokenProvider;
import com.intent.BookStore.unit.util.TestBookDataUtil;
import com.intent.BookStore.unit.util.TestUserDataUtil;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static com.intent.BookStore.unit.util.TestBookDataUtil.*;
import static com.intent.BookStore.unit.util.TestUserDataUtil.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerIntegrationTest extends AbstractTestContainer {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private BookFacadeImpl bookFacade;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository userRepository;

    private MockMvc mockMvc;

    @BeforeAll
    public static void setup() {
        setUp();
    }


    @BeforeEach
    void setUpMVC() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @AfterAll
    public static void tearDownEach() {
        tearDown();
    }

    @Test
    void getAllBooksTest() throws Exception {
        BookDTO book1 = TestBookDataUtil.getBookDTO1().setId(0l);
        BookDTO book2 = TestBookDataUtil.getBookDTO2().setId(0l);
        BookDTO book3 = TestBookDataUtil.getBookDTO3().setId(0l);
        BookDTO createdBookDTO1 = bookFacade.createBook(book1);
        BookDTO createdBookDTO2 = bookFacade.createBook(book2);
        BookDTO createdBookDTO3 = bookFacade.createBook(book3);

        mockMvc.perform(MockMvcRequestBuilders.get("/books")
                        .queryParam("pageNum", String.valueOf(PAGE_NUMBER))
                        .queryParam("pageSize", String.valueOf(PAGE_SIZE)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(3)))
                .andExpect(jsonPath("$.content[0].id", is(createdBookDTO1.getId().intValue())))
                .andExpect(jsonPath("$.content[1].id", is(createdBookDTO2.getId().intValue())))
                .andExpect(jsonPath("$.content[2].id", is(createdBookDTO3.getId().intValue())))
                .andExpect(jsonPath("$.content[0].title", is(book1.getTitle())))
                .andExpect(jsonPath("$.content[1].title", is(book2.getTitle())))
                .andExpect(jsonPath("$.content[2].title", is(book3.getTitle())));
        bookRepository.deleteAll();
    }

    @Test
    void getBookByIdTest() throws Exception {
        BookDTO book = TestBookDataUtil.getBookDTO1().setId(0l);
        BookDTO createdBookDTO = bookFacade.createBook(book);

        mockMvc.perform(MockMvcRequestBuilders.get("/books/{id}", createdBookDTO.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(createdBookDTO.getId().intValue())))
                .andExpect(jsonPath("$.title", is(book.getTitle())))
                .andExpect(jsonPath("$.authorName", is(book.getAuthorName())))
                .andExpect(jsonPath("$.genre", is(book.getGenre())))
                .andExpect(jsonPath("$.price", is(book.getPrice().doubleValue())))
                .andExpect(jsonPath("$.quantity", is(book.getQuantity())))
                .andExpect(jsonPath("$.description", is(book.getDescription())))
                .andExpect(jsonPath("$.imageURL", is(book.getImageURL())));

        bookRepository.deleteAll();
    }

    @Test
    void getBookByTitleTest() throws Exception {
        BookDTO book = TestBookDataUtil.getBookDTO1().setId(0l);
        BookDTO createdBookDTO = bookFacade.createBook(book);

        mockMvc.perform(MockMvcRequestBuilders.get("/books/title/{title}", TITLE_1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(createdBookDTO.getId().intValue())))
                .andExpect(jsonPath("$.title", is(book.getTitle())))
                .andExpect(jsonPath("$.authorName", is(book.getAuthorName())))
                .andExpect(jsonPath("$.genre", is(book.getGenre())))
                .andExpect(jsonPath("$.price", is(book.getPrice().doubleValue())))
                .andExpect(jsonPath("$.quantity", is(book.getQuantity())))
                .andExpect(jsonPath("$.description", is(book.getDescription())))
                .andExpect(jsonPath("$.imageURL", is(book.getImageURL())));

        bookRepository.deleteAll();
    }

    @Test
    void createBookTest() throws Exception {
        User user = TestUserDataUtil.getUser1().setId(0L);
        userRepository.save(user);
        String token = jwtTokenProvider.createToken(USERNAME_1, List.of(ROLE_1.name()));
        BookDTO book = TestBookDataUtil.getBookDTO1().setId(0l);
        mockMvc.perform(post("/books")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .contentType("application/json")
                .content(new ObjectMapper().writeValueAsString(book))
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is(book.getTitle())))
                .andExpect(jsonPath("$.authorName", is(book.getAuthorName())))
                .andExpect(jsonPath("$.genre", is(book.getGenre())))
                .andExpect(jsonPath("$.price", is(book.getPrice().doubleValue())))
                .andExpect(jsonPath("$.quantity", is(book.getQuantity())))
                .andExpect(jsonPath("$.description", is(book.getDescription())))
                .andExpect(jsonPath("$.imageURL", is(book.getImageURL())));

        bookRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void updateBookTest() throws Exception {
        User user = TestUserDataUtil.getUser1().setId(0L);
        userRepository.save(user);
        String token = jwtTokenProvider.createToken(USERNAME_1, List.of(ROLE_1.name()));
        BookDTO book = TestBookDataUtil.getBookDTO1().setId(0l);
        BookDTO createdBookDTO = bookFacade.createBook(book);
        BookDTO bookDTOForUpdate = TestBookDataUtil.getBookDTO2().setId(0L);

        mockMvc.perform(put("/books/{id}", createdBookDTO.getId())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(bookDTOForUpdate))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(createdBookDTO.getId().intValue())))
                .andExpect(jsonPath("$.title", is(bookDTOForUpdate.getTitle())))
                .andExpect(jsonPath("$.authorName", is(bookDTOForUpdate.getAuthorName())))
                .andExpect(jsonPath("$.genre", is(bookDTOForUpdate.getGenre())))
                .andExpect(jsonPath("$.price", is(bookDTOForUpdate.getPrice().doubleValue())))
                .andExpect(jsonPath("$.quantity", is(bookDTOForUpdate.getQuantity())))
                .andExpect(jsonPath("$.description", is(bookDTOForUpdate.getDescription())))
                .andExpect(jsonPath("$.imageURL", is(bookDTOForUpdate.getImageURL())));

        bookRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void getAllBooksByCriteriaTest() throws Exception {
        BookDTO book1 = TestBookDataUtil.getBookDTO1().setId(0l);
        BookDTO book2 = TestBookDataUtil.getBookDTO2().setId(0l);
        BookDTO book3 = TestBookDataUtil.getBookDTO3().setId(0l);
        BookDTO createdBookDTO1 = bookFacade.createBook(book1);
        BookDTO createdBookDTO2 = bookFacade.createBook(book2);
        BookDTO createdBookDTO3 = bookFacade.createBook(book3);

        mockMvc.perform(get("/books/search")
                        .queryParam("authorName", AUTHOR_NAME)
                        .queryParam("genre", GENRE)
                        .queryParam("quantity", String.valueOf(QUANTITY))
                        .queryParam("pageNum", String.valueOf(PAGE_NUMBER))
                        .queryParam("pageSize", String.valueOf(PAGE_SIZE_FOT_CRITERIA)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].id", is(createdBookDTO1.getId().intValue())))
                .andExpect(jsonPath("$.content[1].id", is(createdBookDTO3.getId().intValue())))
                .andExpect(jsonPath("$.content[0].title", is(book1.getTitle())))
                .andExpect(jsonPath("$.content[1].title", is(book3.getTitle())));

        bookRepository.deleteAll();
    }



}
