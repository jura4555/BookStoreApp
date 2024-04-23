package com.intent.BookStore.unit.facade;

import com.intent.BookStore.dto.ChangePasswordDTO;
import com.intent.BookStore.dto.OrderDTO;
import com.intent.BookStore.dto.UserDTO;
import com.intent.BookStore.facade.impl.UserFacadeImpl;
import com.intent.BookStore.model.Order;
import com.intent.BookStore.model.User;
import com.intent.BookStore.service.impl.UserServiceImpl;
import com.intent.BookStore.unit.util.TestOrderDataUtil;
import com.intent.BookStore.unit.util.TestUserDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.intent.BookStore.unit.util.TestUserDataUtil.*;
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

    private static final User.Role ROLE = User.Role.USER;

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
    void updateBookTest() {
        User updatedUser = TestUserDataUtil.getUser1();
        UserDTO userDTO = TestUserDataUtil.getUserDTO1().setId(0L);
        Authentication authentication = new UsernamePasswordAuthenticationToken(updatedUser.getUsername(), "",
                Collections.singleton(new SimpleGrantedAuthority(updatedUser.getRole().name())));
        when(userServiceImpl.getUserByUsername(USERNAME_1)).thenReturn(updatedUser);
        when(userServiceImpl.updateUser(eq(USER_ID_1), any(User.class))).thenReturn(updatedUser);
        UserDTO result = userFacade.updateUser(authentication, userDTO);
        assertThat(result, allOf(
                hasProperty("id", equalTo(USER_ID_1)),
                hasProperty("username", equalTo(userDTO.getUsername())),
                hasProperty("password", equalTo(userDTO.getPassword())),
                hasProperty("email", equalTo(userDTO.getEmail())),
                hasProperty("phoneNumber", equalTo(userDTO.getPhoneNumber())),
                hasProperty("accountBalance", equalTo(userDTO.getAccountBalance()))
        ));
        verify(userServiceImpl, times(1)).getUserByUsername(USERNAME_1);
        verify(userServiceImpl, times(1)).updateUser(eq(USER_ID_1), any(User.class));
    }

    @Test
    void updateUserPasswordTest() {
        User user = TestUserDataUtil.getUser1();
        UserDTO userDTO = TestUserDataUtil.getUserDTO1();
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), "",
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().name())));
        ChangePasswordDTO changePasswordDTO = TestUserDataUtil.getChangePasswordDTO();
        when(userServiceImpl.getUserByUsername(USERNAME_1)).thenReturn(user);
        when(userServiceImpl.changeUserPassword(eq(USER_ID_1), any(ChangePasswordDTO.class))).thenReturn(user);
        UserDTO result = userFacade.updateUserPassword(authentication, changePasswordDTO);
        assertThat(result, allOf(
                hasProperty("id", equalTo(userDTO.getId())),
                hasProperty("username", equalTo(userDTO.getUsername())),
                hasProperty("password", equalTo(userDTO.getPassword())),
                hasProperty("email", equalTo(userDTO.getEmail())),
                hasProperty("phoneNumber", equalTo(userDTO.getPhoneNumber())),
                hasProperty("accountBalance", equalTo(userDTO.getAccountBalance()))
        ));
        verify(userServiceImpl, times(1)).getUserByUsername(USERNAME_1);
        verify(userServiceImpl, times(1)).changeUserPassword(eq(USER_ID_1), any(ChangePasswordDTO.class));
    }

    @Test
    void increaseAccountBalanceTest() {
        User user = TestUserDataUtil.getUser1();
        UserDTO userDTO = TestUserDataUtil.getUserDTO1();
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), "",
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().name())));
        when(userServiceImpl.getUserByUsername(USERNAME_1)).thenReturn(user);
        when(userServiceImpl.increaseAccountBalance(USER_ID_1, AMOUNT)).thenReturn(user);
        UserDTO result = userFacade.increaseAccountBalance(authentication, AMOUNT);
        assertThat(result, allOf(
                hasProperty("id", equalTo(userDTO.getId())),
                hasProperty("username", equalTo(userDTO.getUsername())),
                hasProperty("password", equalTo(userDTO.getPassword())),
                hasProperty("email", equalTo(userDTO.getEmail())),
                hasProperty("phoneNumber", equalTo(userDTO.getPhoneNumber())),
                hasProperty("accountBalance", equalTo(userDTO.getAccountBalance()))
        ));
        verify(userServiceImpl, times(1)).getUserByUsername(USERNAME_1);
        verify(userServiceImpl, times(1)).increaseAccountBalance(USER_ID_1, AMOUNT);
    }

    @Test
    void updateRoleTest() {
        User user = TestUserDataUtil.getUser2();
        UserDTO userDTO = TestUserDataUtil.getUserDTO2();
        when(userServiceImpl.updateRole(USER_ID_2, ROLE.name())).thenReturn(user);
        UserDTO result = userFacade.updateRole(USER_ID_2, ROLE.name());
        assertThat(result, allOf(
                hasProperty("id", equalTo(userDTO.getId())),
                hasProperty("username", equalTo(userDTO.getUsername())),
                hasProperty("password", equalTo(userDTO.getPassword())),
                hasProperty("email", equalTo(userDTO.getEmail())),
                hasProperty("phoneNumber", equalTo(userDTO.getPhoneNumber())),
                hasProperty("accountBalance", equalTo(userDTO.getAccountBalance()))
        ));
        verify(userServiceImpl, times(1)).updateRole(USER_ID_2, ROLE.name());
    }
}
