package com.intent.BookStore.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intent.BookStore.dto.LoginDTO;
import com.intent.BookStore.integration.AbstractTestContainer;
import com.intent.BookStore.model.User;
import com.intent.BookStore.repository.UserRepository;
import com.intent.BookStore.unit.util.TestUserDataUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthenticationControllerIntegrationTest extends AbstractTestContainer {

    @Autowired
    private WebApplicationContext context;

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
    @Transactional
    void registerTest() throws Exception {
        User user = TestUserDataUtil.getUser1().setId(0L)
                .setAccountBalance(null)
                .setRole(null)
                .setOrders(null);

        mockMvc.perform(post("/register")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(user))
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.email", is(user.getEmail())))
                .andExpect(jsonPath("$.password", is(user.getPassword())))
                .andExpect(jsonPath("$.phoneNumber", is(user.getPhoneNumber())))
                .andExpect(jsonPath("$.accountBalance", is(BigDecimal.ZERO.intValue())))
                .andExpect(jsonPath("$.role", is(User.Role.USER.name())));

        userRepository.deleteAll();
    }

    @Test
    @Transactional
    void loginTest() throws Exception {
        User user = TestUserDataUtil.getUser1().setId(0L)
                .setAccountBalance(BigDecimal.ZERO)
                .setOrders(null);
        LoginDTO loginDTO = TestUserDataUtil.getLoginDTO1();

        userRepository.save(user);

        mockMvc.perform(post("/login")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(loginDTO))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(notNullValue())));

        userRepository.deleteAll();

    }


}
