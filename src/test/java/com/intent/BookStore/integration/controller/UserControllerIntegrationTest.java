package com.intent.BookStore.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intent.BookStore.dto.ChangePasswordDTO;
import com.intent.BookStore.dto.UserDTO;
import com.intent.BookStore.integration.AbstractTestContainer;
import com.intent.BookStore.model.User;
import com.intent.BookStore.repository.UserRepository;
import com.intent.BookStore.security.jwt.JwtTokenProvider;
import com.intent.BookStore.unit.util.TestUserDataUtil;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import java.math.BigDecimal;
import java.util.List;

import static com.intent.BookStore.unit.util.TestUserDataUtil.ROLE_1;
import static com.intent.BookStore.unit.util.TestUserDataUtil.USERNAME_1;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerIntegrationTest extends AbstractTestContainer {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

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

    private static final int PAGE_NUMBER = 1;
    private static final int PAGE_SIZE = 2;
    private static final BigDecimal UPDATED_ACCOUNT_BALANCE = BigDecimal.valueOf(5.00);

    @Test
    @Transactional
    void getAllUsersTest() throws Exception {
        User user1 = TestUserDataUtil.getUser1().setId(0L)
                .setAccountBalance(BigDecimal.ZERO)
                .setOrders(null);
        User user2 = TestUserDataUtil.getUser2().setId(0L)
                .setAccountBalance(BigDecimal.ZERO)
                .setOrders(null);
        String token = jwtTokenProvider.createToken(USERNAME_1, List.of(ROLE_1.name()));

        User createdUser1 = userRepository.save(user1);
        User createdUser2 = userRepository.save(user2);

        mockMvc.perform(get("/users")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .queryParam("pageNum", String.valueOf(PAGE_NUMBER))
                        .queryParam("pageSize", String.valueOf(PAGE_SIZE)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(PAGE_SIZE)))
                .andExpect(jsonPath("$.content[0].id", is(createdUser1.getId().intValue())))
                .andExpect(jsonPath("$.content[1].id", is(createdUser2.getId().intValue())))
                .andExpect(jsonPath("$.content[0].username", is(createdUser1.getUsername())))
                .andExpect(jsonPath("$.content[1].username", is(createdUser2.getUsername())));

        userRepository.deleteAll();
    }

    @Test
    @Transactional
    void getUserByIdTest() throws Exception {
        User user = TestUserDataUtil.getUser1().setId(0L)
                .setAccountBalance(BigDecimal.ZERO)
                .setOrders(null);
        String token = jwtTokenProvider.createToken(USERNAME_1, List.of(ROLE_1.name()));
        User createdUser = userRepository.save(user);

        mockMvc.perform(get("/users/{id}", createdUser.getId())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(createdUser.getId().intValue())))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.email", is(user.getEmail())))
                .andExpect(jsonPath("$.password", is(user.getPassword())))
                .andExpect(jsonPath("$.phoneNumber", is(user.getPhoneNumber())))
                .andExpect(jsonPath("$.accountBalance", is(user.getAccountBalance().intValue())))
                .andExpect(jsonPath("$.role", is(user.getRole().name())));

        userRepository.deleteAll();
    }

    @Test
    @Transactional
    void getUserByUsernameTest() throws Exception {
        User user = TestUserDataUtil.getUser1().setId(0L)
                .setAccountBalance(BigDecimal.ZERO)
                .setOrders(null);
        String token = jwtTokenProvider.createToken(USERNAME_1, List.of(ROLE_1.name()));
        User createdUser = userRepository.save(user);

        mockMvc.perform(get("/users/name/{username}", createdUser.getUsername())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(createdUser.getId().intValue())))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.email", is(user.getEmail())))
                .andExpect(jsonPath("$.password", is(user.getPassword())))
                .andExpect(jsonPath("$.phoneNumber", is(user.getPhoneNumber())))
                .andExpect(jsonPath("$.accountBalance", is(user.getAccountBalance().intValue())))
                .andExpect(jsonPath("$.role", is(user.getRole().name())));

        userRepository.deleteAll();
    }

    @Test
    @Transactional
    void getUserTest() throws Exception {
        User user = TestUserDataUtil.getUser1().setId(0L)
                .setAccountBalance(BigDecimal.ZERO)
                .setOrders(null);
        String token = jwtTokenProvider.createToken(USERNAME_1, List.of(ROLE_1.name()));
        User createdUser = userRepository.save(user);

        mockMvc.perform(get("/users/me")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(createdUser.getId().intValue())))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.email", is(user.getEmail())))
                .andExpect(jsonPath("$.password", is(user.getPassword())))
                .andExpect(jsonPath("$.phoneNumber", is(user.getPhoneNumber())))
                .andExpect(jsonPath("$.accountBalance", is(user.getAccountBalance().intValue())))
                .andExpect(jsonPath("$.role", is(user.getRole().name())));

        userRepository.deleteAll();
    }

//    @Test
//    @Transactional
//    void createUserTest() throws Exception {
//        UserDTO user = TestUserDataUtil.getUserDTO1().setId(0L)
//                .setAccountBalance(null)
//                .setOrders(null);
//
//        mockMvc.perform(post("/users")
//                        .contentType("application/json")
//                        .content(new ObjectMapper().writeValueAsString(user))
//                )
//                .andDo(print())
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.username", is(user.getUsername())))
//                .andExpect(jsonPath("$.email", is(user.getEmail())))
//                .andExpect(jsonPath("$.password", is(user.getPassword())))
//                .andExpect(jsonPath("$.phoneNumber", is(user.getPhoneNumber())))
//                .andExpect(jsonPath("$.accountBalance", is(DEFAULT_ACCOUNT_BALANCE)));
//
//        userRepository.deleteAll();
//    }

    @Test
    @Transactional
    void updateUserTest() throws Exception {
        User user = TestUserDataUtil.getUser1().setId(0L)
                .setAccountBalance(BigDecimal.ZERO)
                .setOrders(null);
        User createdUser = userRepository.save(user);
        UserDTO userDTOForUpdate = TestUserDataUtil.getUserDTO2().setId(0L)
                .setPassword(null)
                .setAccountBalance(null)
                .setRole(null)
                .setOrders(null);
        String token = jwtTokenProvider.createToken(USERNAME_1, List.of(ROLE_1.name()));

        mockMvc.perform(put("/users/me")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(userDTOForUpdate))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(createdUser.getId().intValue())))
                .andExpect(jsonPath("$.username", is(userDTOForUpdate.getUsername())))
                .andExpect(jsonPath("$.email", is(userDTOForUpdate.getEmail())))
                .andExpect(jsonPath("$.password", is(createdUser.getPassword())))
                .andExpect(jsonPath("$.phoneNumber", is(userDTOForUpdate.getPhoneNumber())))
                .andExpect(jsonPath("$.role", is(user.getRole().name())));

        userRepository.deleteAll();
    }

    @Test
    @Transactional
    void updateUserPasswordTest() throws Exception {
        User user = TestUserDataUtil.getUser1().setId(0L)
                .setAccountBalance(BigDecimal.ZERO)
                .setOrders(null);
        User createdUser = userRepository.save(user);
        ChangePasswordDTO changePasswordDTO = TestUserDataUtil.getChangePasswordDTO();
        String token = jwtTokenProvider.createToken(USERNAME_1, List.of(ROLE_1.name()));

        mockMvc.perform(patch("/users/me/password")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(changePasswordDTO))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(createdUser.getId().intValue())))
                .andExpect(jsonPath("$.username", is(createdUser.getUsername())))
                .andExpect(jsonPath("$.email", is(createdUser.getEmail())))
                .andExpect(jsonPath("$.password", is(changePasswordDTO.getNewPassword())))
                .andExpect(jsonPath("$.phoneNumber", is(createdUser.getPhoneNumber())))
                .andExpect(jsonPath("$.role", is(createdUser.getRole().name())));

        userRepository.deleteAll();
    }

    @Test
    @Transactional
    void increaseAccountBalanceTest() throws Exception {
        User user = TestUserDataUtil.getUser1().setId(0L)
                .setAccountBalance(BigDecimal.ZERO)
                .setOrders(null);
        String token = jwtTokenProvider.createToken(USERNAME_1, List.of(ROLE_1.name()));

        User createdUser = userRepository.save(user);

        mockMvc.perform(patch("/users/me/balance")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .queryParam("amount", String.valueOf(UPDATED_ACCOUNT_BALANCE))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(createdUser.getId().intValue())))
                .andExpect(jsonPath("$.username", is(createdUser.getUsername())))
                .andExpect(jsonPath("$.email", is(createdUser.getEmail())))
                .andExpect(jsonPath("$.password", is(createdUser.getPassword())))
                .andExpect(jsonPath("$.phoneNumber", is(createdUser.getPhoneNumber())))
                .andExpect(jsonPath("$.accountBalance", is(UPDATED_ACCOUNT_BALANCE.doubleValue())))
                .andExpect(jsonPath("$.role", is(createdUser.getRole().name())));

        userRepository.deleteAll();
    }

    @Test
    @Transactional
    void updateUserRoleTest() throws Exception {
        User admin = TestUserDataUtil.getUser1().setId(0L)
                .setAccountBalance(BigDecimal.ZERO)
                .setOrders(null);
        User user = TestUserDataUtil.getUser2().setId(0L)
                .setAccountBalance(BigDecimal.ZERO)
                .setOrders(null);

        String token = jwtTokenProvider.createToken(USERNAME_1, List.of(ROLE_1.name()));

        userRepository.save(admin);
        User createdUser = userRepository.save(user);

        mockMvc.perform(patch("/users/{id}/role", createdUser.getId())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .queryParam("role", User.Role.USER.name())
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(createdUser.getId().intValue())))
                .andExpect(jsonPath("$.username", is(createdUser.getUsername())))
                .andExpect(jsonPath("$.email", is(createdUser.getEmail())))
                .andExpect(jsonPath("$.password", is(createdUser.getPassword())))
                .andExpect(jsonPath("$.phoneNumber", is(createdUser.getPhoneNumber())))
                .andExpect(jsonPath("$.accountBalance", is(createdUser.getAccountBalance().intValue())))
                .andExpect(jsonPath("$.role", is(User.Role.USER.name())));

        userRepository.deleteAll();
    }


}
