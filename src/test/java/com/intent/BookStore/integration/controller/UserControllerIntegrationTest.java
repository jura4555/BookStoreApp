package com.intent.BookStore.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intent.BookStore.aspect.ControllerAspect;
import com.intent.BookStore.aws.DynamoClient;
import com.intent.BookStore.config.DynamoDBConfig;
import com.intent.BookStore.controller.UserController;
import com.intent.BookStore.dto.ChangePasswordDTO;
import com.intent.BookStore.dto.UserDTO;
import com.intent.BookStore.facade.impl.UserFacadeImpl;
import com.intent.BookStore.integration.AbstractTestContainer;
import com.intent.BookStore.repository.UserRepository;
import com.intent.BookStore.unit.util.TestUserDataUtil;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.math.BigDecimal;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@MockBeans({ @MockBean(ControllerAspect.class), @MockBean(DynamoClient.class), @MockBean(DynamoDBConfig.class) })
class UserControllerIntegrationTest extends AbstractTestContainer {

    @Autowired
    private UserFacadeImpl userFacade;

    @Autowired
    private UserRepository userRepository;

    private MockMvc mockMvc;

    @BeforeAll
    public static void setup() {
        setUp();
    }

    @BeforeEach
    public void setupMVC() {
        mockMvc = MockMvcBuilders.standaloneSetup(new UserController(userFacade)).build();
    }

    @AfterAll
    public static void tearDownEach() {
        tearDown();
    }

    private static final int PAGE_NUMBER = 1;
    private static final int PAGE_SIZE = 2;

    private static final int DEFAULT_ACCOUNT_BALANCE = 0;

    private static final BigDecimal UPDATED_ACCOUNT_BALANCE = BigDecimal.valueOf(5.00);

    @Test
    @Transactional
    void getAllUsersTest() throws Exception {
        UserDTO user1 = TestUserDataUtil.getUserDTO1().setId(0L)
                .setAccountBalance(BigDecimal.ZERO)
                .setOrders(null);
        UserDTO user2 = TestUserDataUtil.getUserDTO2().setId(0L)
                .setAccountBalance(BigDecimal.ZERO)
                .setOrders(null);
        UserDTO createdUserDTO1 = userFacade.createUser(user1);
        UserDTO createdUserDTO2 = userFacade.createUser(user2);

        mockMvc.perform(get("/users")
                        .queryParam("pageNum", String.valueOf(PAGE_NUMBER))
                        .queryParam("pageSize", String.valueOf(PAGE_SIZE)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(PAGE_SIZE)))
                .andExpect(jsonPath("$.content[0].id", is(createdUserDTO1.getId().intValue())))
                .andExpect(jsonPath("$.content[1].id", is(createdUserDTO2.getId().intValue())))
                .andExpect(jsonPath("$.content[0].username", is(user1.getUsername())))
                .andExpect(jsonPath("$.content[1].username", is(user2.getUsername())));

        userRepository.deleteAll();
    }

    @Test
    @Transactional
    void getUserByIdTest() throws Exception {
        UserDTO user = TestUserDataUtil.getUserDTO1().setId(0L)
                .setAccountBalance(BigDecimal.ZERO)
                .setOrders(null);
        UserDTO createdUserDTO = userFacade.createUser(user);

        mockMvc.perform(get("/users/{id}", createdUserDTO.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(createdUserDTO.getId().intValue())))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.email", is(user.getEmail())))
                .andExpect(jsonPath("$.password", is(user.getPassword())))
                .andExpect(jsonPath("$.phoneNumber", is(user.getPhoneNumber())))
                .andExpect(jsonPath("$.accountBalance", is(user.getAccountBalance().intValue())));

        userRepository.deleteAll();
    }

    @Test
    @Transactional
    void getUserByUsernameTest() throws Exception {
        UserDTO user = TestUserDataUtil.getUserDTO1().setId(0L)
                .setAccountBalance(BigDecimal.ZERO)
                .setOrders(null);
        UserDTO createdUserDTO = userFacade.createUser(user);

        mockMvc.perform(get("/users/name/{username}", createdUserDTO.getUsername()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(createdUserDTO.getId().intValue())))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.email", is(user.getEmail())))
                .andExpect(jsonPath("$.password", is(user.getPassword())))
                .andExpect(jsonPath("$.phoneNumber", is(user.getPhoneNumber())))
                .andExpect(jsonPath("$.accountBalance", is(user.getAccountBalance().intValue())));

        userRepository.deleteAll();
    }

    @Test
    @Transactional
    void createUserTest() throws Exception {
        UserDTO user = TestUserDataUtil.getUserDTO1().setId(0L)
                .setAccountBalance(null)
                .setOrders(null);

        mockMvc.perform(post("/users")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(user))
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.email", is(user.getEmail())))
                .andExpect(jsonPath("$.password", is(user.getPassword())))
                .andExpect(jsonPath("$.phoneNumber", is(user.getPhoneNumber())))
                .andExpect(jsonPath("$.accountBalance", is(DEFAULT_ACCOUNT_BALANCE)));

        userRepository.deleteAll();
    }

    @Test
    @Transactional
    void updateUserTest() throws Exception {
        UserDTO user = TestUserDataUtil.getUserDTO1().setId(0L)
                .setAccountBalance(null)
                .setOrders(null);
        UserDTO createdUserDTO = userFacade.createUser(user);
        UserDTO userDTOForUpdate = TestUserDataUtil.getUserDTO2().setId(0L)
                .setPassword(null)
                .setAccountBalance(null)
                .setOrders(null);

        mockMvc.perform(put("/users/{id}", createdUserDTO.getId())
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(userDTOForUpdate))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(createdUserDTO.getId().intValue())))
                .andExpect(jsonPath("$.username", is(userDTOForUpdate.getUsername())))
                .andExpect(jsonPath("$.email", is(userDTOForUpdate.getEmail())))
                .andExpect(jsonPath("$.password", is(createdUserDTO.getPassword())))
                .andExpect(jsonPath("$.phoneNumber", is(userDTOForUpdate.getPhoneNumber())));

        userRepository.deleteAll();
    }

    @Test
    @Transactional
    void updateUserPasswordTest() throws Exception {
        UserDTO user = TestUserDataUtil.getUserDTO1().setId(0L)
                .setAccountBalance(null)
                .setOrders(null);
        UserDTO createdUserDTO = userFacade.createUser(user);
        ChangePasswordDTO changePasswordDTO = TestUserDataUtil.getChangePasswordDTO();

        mockMvc.perform(patch("/users/{id}", createdUserDTO.getId())
                .contentType("application/json")
                .content(new ObjectMapper().writeValueAsString(changePasswordDTO))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(createdUserDTO.getId().intValue())))
                .andExpect(jsonPath("$.username", is(createdUserDTO.getUsername())))
                .andExpect(jsonPath("$.email", is(createdUserDTO.getEmail())))
                .andExpect(jsonPath("$.password", is(changePasswordDTO.getNewPassword())))
                .andExpect(jsonPath("$.phoneNumber", is(createdUserDTO.getPhoneNumber())));

        userRepository.deleteAll();
    }

    @Test
    @Transactional
    void increaseAccountBalanceTest() throws Exception {
        UserDTO user = TestUserDataUtil.getUserDTO1().setId(0L)
                .setAccountBalance(null)
                .setOrders(null);
        UserDTO createdUserDTO = userFacade.createUser(user);

        mockMvc.perform(patch("/users/{id}/increase-balance", createdUserDTO.getId())
                        .queryParam("amount", String.valueOf(UPDATED_ACCOUNT_BALANCE))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(createdUserDTO.getId().intValue())))
                .andExpect(jsonPath("$.username", is(createdUserDTO.getUsername())))
                .andExpect(jsonPath("$.email", is(createdUserDTO.getEmail())))
                .andExpect(jsonPath("$.password", is(createdUserDTO.getPassword())))
                .andExpect(jsonPath("$.phoneNumber", is(createdUserDTO.getPhoneNumber())))
                .andExpect(jsonPath("$.accountBalance", is(UPDATED_ACCOUNT_BALANCE.doubleValue())));

        userRepository.deleteAll();
    }


}
