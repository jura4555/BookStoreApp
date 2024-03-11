package com.intent.BookStore.unit.facade;

import com.intent.BookStore.dto.BookDTO;
import com.intent.BookStore.dto.ChangePasswordDTO;
import com.intent.BookStore.dto.OrderDTO;
import com.intent.BookStore.dto.UserDTO;
import com.intent.BookStore.facade.impl.UserFacadeImpl;
import com.intent.BookStore.model.Book;
import com.intent.BookStore.model.Order;
import com.intent.BookStore.model.User;
import com.intent.BookStore.service.impl.UserServiceImpl;
import com.intent.BookStore.unit.util.TestBookDataUtil;
import com.intent.BookStore.unit.util.TestOrderDataUtil;
import com.intent.BookStore.unit.util.TestUserDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.intent.BookStore.unit.util.TestUserDataUtil.USERNAME_1;
import static com.intent.BookStore.unit.util.TestUserDataUtil.USER_ID_1;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserFacadeImplTest {

    @Mock
    private UserServiceImpl userServiceImpl;

    @InjectMocks
    private UserFacadeImpl userFacade;

    private static final int PAGE_NUMBER_DEFAULT = 0;
    private static final int PAGE_NUMBER = 1;
    private static final int PAGE_SIZE = 2;

    private static final BigDecimal AMOUNT = BigDecimal.valueOf(100.00);

    @Test
    void getAllUserTest() {
        User user1 = TestUserDataUtil.getUser1();
        User user2 = TestUserDataUtil.getUser2();

        UserDTO userDTO1 = TestUserDataUtil.getUserDTO1();
        UserDTO userDTO2 = TestUserDataUtil.getUserDTO2();

        Pageable pageable = PageRequest.of(PAGE_NUMBER_DEFAULT, PAGE_SIZE, Sort.by("id").ascending());
        Page<User> users = new PageImpl<>(List.of(user1, user2), pageable, 2);

        when(userServiceImpl.getAllUsers(PAGE_NUMBER, PAGE_SIZE)).thenReturn(users);
        Page<UserDTO> result = userFacade.getAllUsers(PAGE_NUMBER, PAGE_SIZE);
        assertThat(result.getContent().size(), is(users.getNumberOfElements()));
        assertThat(result, contains(userDTO1, userDTO2));
        verify(userServiceImpl, times(1)).getAllUsers(PAGE_NUMBER, PAGE_SIZE);
    }

    @Test
    void getUserByIdTest() {
        Set<Order> orders = new HashSet<>();
        Order order = TestOrderDataUtil.getOrder1();
        orders.add(order);
        Set<OrderDTO> orderDTOs = new HashSet<>();
        OrderDTO orderDTO = TestOrderDataUtil.getOrderDTO1();
        orderDTOs.add(orderDTO);
        User user = TestUserDataUtil.getUser1().setOrders(orders);
        UserDTO expectedUserDTO = TestUserDataUtil.getUserDTO1().setOrders(orderDTOs);
        when(userServiceImpl.getUserById(USER_ID_1)).thenReturn(user);
        UserDTO result = userFacade.getUserById(USER_ID_1);
        assertThat(result, allOf(
                hasProperty("id", equalTo(expectedUserDTO.getId())),
                hasProperty("username", equalTo(expectedUserDTO.getUsername())),
                hasProperty("password", equalTo(expectedUserDTO.getPassword())),
                hasProperty("email", equalTo(expectedUserDTO.getEmail())),
                hasProperty("phoneNumber", equalTo(expectedUserDTO.getPhoneNumber())),
                hasProperty("accountBalance", equalTo(expectedUserDTO.getAccountBalance())),
                hasProperty("orders", equalTo(expectedUserDTO.getOrders()))
                ));
        verify(userServiceImpl, times(1)).getUserById(USER_ID_1);
    }

    @Test
    void getUserByUsernameTest() {
        User user = TestUserDataUtil.getUser1();
        UserDTO expectedUserDTO = TestUserDataUtil.getUserDTO1();
        when(userServiceImpl.getUserByUsername(USERNAME_1)).thenReturn(user);
        UserDTO result = userFacade.getUserByUsername(USERNAME_1);
        assertThat(result, allOf(
                hasProperty("id", equalTo(expectedUserDTO.getId())),
                hasProperty("username", equalTo(expectedUserDTO.getUsername())),
                hasProperty("password", equalTo(expectedUserDTO.getPassword())),
                hasProperty("email", equalTo(expectedUserDTO.getEmail())),
                hasProperty("phoneNumber", equalTo(expectedUserDTO.getPhoneNumber())),
                hasProperty("accountBalance", equalTo(expectedUserDTO.getAccountBalance()))
        ));
        verify(userServiceImpl, times(1)).getUserByUsername(USERNAME_1);
    }

    @Test
    void createUserTest() {
        User createdUser = TestUserDataUtil.getUser1();
        UserDTO userDTO = TestUserDataUtil.getUserDTO1().setId(0L);
        when(userServiceImpl.createUser(any(User.class))).thenReturn(createdUser);
        UserDTO result = userFacade.createUser(userDTO);
        assertThat(result, allOf(
                hasProperty("id", equalTo(USER_ID_1)),
                hasProperty("username", equalTo(userDTO.getUsername())),
                hasProperty("password", equalTo(userDTO.getPassword())),
                hasProperty("email", equalTo(userDTO.getEmail())),
                hasProperty("phoneNumber", equalTo(userDTO.getPhoneNumber())),
                hasProperty("accountBalance", equalTo(userDTO.getAccountBalance()))
        ));
        verify(userServiceImpl, times(1)).createUser(any(User.class));
    }

    @Test
    void updateBookTest() {
        User updatedUser = TestUserDataUtil.getUser1();
        UserDTO userDTO = TestUserDataUtil.getUserDTO1().setId(0L);
        when(userServiceImpl.updateUser(eq(USER_ID_1), any(User.class))).thenReturn(updatedUser);
        UserDTO result = userFacade.updateUser(USER_ID_1, userDTO);
        assertThat(result, allOf(
                hasProperty("id", equalTo(USER_ID_1)),
                hasProperty("username", equalTo(userDTO.getUsername())),
                hasProperty("password", equalTo(userDTO.getPassword())),
                hasProperty("email", equalTo(userDTO.getEmail())),
                hasProperty("phoneNumber", equalTo(userDTO.getPhoneNumber())),
                hasProperty("accountBalance", equalTo(userDTO.getAccountBalance()))
        ));
        verify(userServiceImpl, times(1)).updateUser(eq(USER_ID_1), any(User.class));
    }

    @Test
    void updateUserPasswordTest() {
        User user = TestUserDataUtil.getUser1();
        UserDTO userDTO = TestUserDataUtil.getUserDTO1();
        ChangePasswordDTO changePasswordDTO = TestUserDataUtil.getChangePasswordDTO();
        when(userServiceImpl.changeUserPassword(eq(USER_ID_1), any(ChangePasswordDTO.class))).thenReturn(user);
        UserDTO result = userFacade.updateUserPassword(USER_ID_1, changePasswordDTO);
        assertThat(result, allOf(
                hasProperty("id", equalTo(userDTO.getId())),
                hasProperty("username", equalTo(userDTO.getUsername())),
                hasProperty("password", equalTo(userDTO.getPassword())),
                hasProperty("email", equalTo(userDTO.getEmail())),
                hasProperty("phoneNumber", equalTo(userDTO.getPhoneNumber())),
                hasProperty("accountBalance", equalTo(userDTO.getAccountBalance()))
        ));
        verify(userServiceImpl, times(1)).changeUserPassword(eq(USER_ID_1), any(ChangePasswordDTO.class));
    }

    @Test
    void increaseAccountBalanceTest() {
        User user = TestUserDataUtil.getUser1();
        UserDTO userDTO = TestUserDataUtil.getUserDTO1();
        when(userServiceImpl.increaseAccountBalance(USER_ID_1, AMOUNT)).thenReturn(user);
        UserDTO result = userFacade.increaseAccountBalance(USER_ID_1, AMOUNT);
        assertThat(result, allOf(
                hasProperty("id", equalTo(userDTO.getId())),
                hasProperty("username", equalTo(userDTO.getUsername())),
                hasProperty("password", equalTo(userDTO.getPassword())),
                hasProperty("email", equalTo(userDTO.getEmail())),
                hasProperty("phoneNumber", equalTo(userDTO.getPhoneNumber())),
                hasProperty("accountBalance", equalTo(userDTO.getAccountBalance()))
        ));
        verify(userServiceImpl, times(1)).increaseAccountBalance(USER_ID_1, AMOUNT);


    }

}
