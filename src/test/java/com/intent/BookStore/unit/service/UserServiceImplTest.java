package com.intent.BookStore.unit.service;

import com.intent.BookStore.dto.ChangePasswordDTO;
import com.intent.BookStore.exception.IncorrectPasswordException;
import com.intent.BookStore.exception.PasswordMismatchException;
import com.intent.BookStore.exception.UserNotFoundException;
import com.intent.BookStore.model.User;
import com.intent.BookStore.repository.UserRepository;
import com.intent.BookStore.service.impl.UserServiceImpl;
import com.intent.BookStore.unit.util.TestUserDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.intent.BookStore.unit.util.TestUserDataUtil.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private  static final String USERNAME_UPDATE = "alex_brown";
    private static final String PHONE_UPDATE = "+380681714959";
    private static final BigDecimal AMOUNT = BigDecimal.valueOf(100.00);

    @Test
    void getAllUserTest() {
        User user1 = TestUserDataUtil.getUser1();
        User user2 = TestUserDataUtil.getUser2();
        Pageable pageable = PageRequest.of(0, 2, Sort.by("id").ascending());
        Page<User> users = new PageImpl<>(List.of(user1, user2), pageable, 2);

        when(userRepository.findAll(pageable)).thenReturn(users);

        Page<User> result = userService.getAllUsers(1, 2);
        assertThat(result.getTotalElements(), is((long) users.getNumberOfElements()));
        assertThat(result, contains(user1, user2));
        verify(userRepository, times(1)).findAll(pageable);
    }

    @Test
    void getUserByIdTest() {
        User user = TestUserDataUtil.getUser1();
        when(userRepository.findById(USER_ID_1)).thenReturn(Optional.of(user));
        User result = userService.getUserById(USER_ID_1);
        assertThat(result, allOf(
                hasProperty("id", equalTo(user.getId())),
                hasProperty("username", equalTo(user.getUsername())),
                hasProperty("password", equalTo(user.getPassword())),
                hasProperty("email", equalTo(user.getEmail())),
                hasProperty("phoneNumber", equalTo(user.getPhoneNumber())),
                hasProperty("accountBalance", equalTo(user.getAccountBalance()))
        ));
        verify(userRepository, times(1)).findById(USER_ID_1);
    }

    @Test
    void getUserByIdWithNotFoundExceptionTest(){
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(anyLong()));
    }

    @Test
    void getUserByUsernameTest() {
        User user = TestUserDataUtil.getUser1();
        when(userRepository.findByUsername(USERNAME_1)).thenReturn(Optional.of(user));
        User result = userService.getUserByUsername(USERNAME_1);
        assertThat(result, allOf(
                hasProperty("id", equalTo(user.getId())),
                hasProperty("username", equalTo(user.getUsername())),
                hasProperty("password", equalTo(user.getPassword())),
                hasProperty("email", equalTo(user.getEmail())),
                hasProperty("phoneNumber", equalTo(user.getPhoneNumber())),
                hasProperty("accountBalance", equalTo(user.getAccountBalance()))
        ));
        verify(userRepository, times(1)).findByUsername(USERNAME_1);
    }

    @Test
    void getUserByUsernameWithNotFoundExceptionTest(){
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUserByUsername(anyString()));
    }


    @Test
    void createUserTest() {
        User user = TestUserDataUtil.getUser1().setId(0L);
        User createdUser = TestUserDataUtil.getUser1();
        when(userRepository.save(user)).thenReturn(createdUser);
        User result = userService.createUser(user);
        assertThat(result, allOf(
                hasProperty("id", equalTo(createdUser.getId())),
                hasProperty("username", equalTo(createdUser.getUsername())),
                hasProperty("password", equalTo(createdUser.getPassword())),
                hasProperty("email", equalTo(createdUser.getEmail())),
                hasProperty("phoneNumber", equalTo(createdUser.getPhoneNumber())),
                hasProperty("accountBalance", equalTo(createdUser.getAccountBalance()))
        ));
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void updateUserTest() {
        User user = TestUserDataUtil.getUser1();
        User updatedUser = TestUserDataUtil.getUser1()
                .setUsername(USERNAME_UPDATE)
                .setPhoneNumber(PHONE_UPDATE);
        when(userRepository.findById(USER_ID_1)).thenReturn(Optional.of(user));
        when(userRepository.save(updatedUser)).thenReturn(updatedUser);
        User result = userService.updateUser(USER_ID_1, updatedUser);
        assertThat(result, allOf(
                hasProperty("id", equalTo(updatedUser.getId())),
                hasProperty("username", equalTo(updatedUser.getUsername())),
                hasProperty("password", equalTo(updatedUser.getPassword())),
                hasProperty("email", equalTo(updatedUser.getEmail())),
                hasProperty("phoneNumber", equalTo(updatedUser.getPhoneNumber())),
                hasProperty("accountBalance", equalTo(updatedUser.getAccountBalance()))
        ));
        verify(userRepository, times(1)).findById(USER_ID_1);
        verify(userRepository, times(1)).save(updatedUser);
    }

    @Test
    void changeUserPasswordTest() {
        User user = TestUserDataUtil.getUser1();
        ChangePasswordDTO changePasswordDTO = TestUserDataUtil.getChangePasswordDTO();
        when(userRepository.findById(USER_ID_1)).thenReturn(Optional.of(user));
        user.setPassword(PASSWORD_NEW);
        when(userRepository.save(user)).thenReturn(user);
        user.setPassword(PASSWORD_1);
        User result = userService.changeUserPassword(USER_ID_1, changePasswordDTO);
        assertThat(result, allOf(
                hasProperty("id", equalTo(user.getId())),
                hasProperty("username", equalTo(user.getUsername())),
                hasProperty("password", equalTo(user.getPassword())),
                hasProperty("email", equalTo(user.getEmail())),
                hasProperty("phoneNumber", equalTo(user.getPhoneNumber())),
                hasProperty("accountBalance", equalTo(user.getAccountBalance()))
        ));
        verify(userRepository, times(1)).findById(USER_ID_1);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void changeUserPasswordWithIncorrectPasswordExceptionTest(){
        ChangePasswordDTO changePasswordDTO = TestUserDataUtil.getChangePasswordDTO();
        User user = TestUserDataUtil.getUser1().setPassword(PASSWORD_NEW);
        when(userRepository.findById(USER_ID_1)).thenReturn(Optional.of(user));
        assertThrows(IncorrectPasswordException.class, () -> userService.changeUserPassword(USER_ID_1, changePasswordDTO));
    }

    @Test
    void changeUserPasswordWithPasswordMismatchExceptionTest(){
        ChangePasswordDTO changePasswordDTO = TestUserDataUtil.getChangePasswordDTO()
                .setConfirmPassword(PASSWORD_2);
        User user = TestUserDataUtil.getUser1();
        when(userRepository.findById(USER_ID_1)).thenReturn(Optional.of(user));
        assertThrows(PasswordMismatchException.class, () -> userService.changeUserPassword(USER_ID_1, changePasswordDTO));
    }

    @Test
    void increaseAccountBalanceTest() {
        User user = TestUserDataUtil.getUser1();
        when(userRepository.findById(USER_ID_1)).thenReturn(Optional.of(user));
        user.setAccountBalance(ACCOUNT_BALANCE_1.add(AMOUNT));
        when(userRepository.save(user)).thenReturn(user);
        User result = userService.increaseAccountBalance(USER_ID_1, AMOUNT);
        assertThat(result, allOf(
                hasProperty("id", equalTo(user.getId())),
                hasProperty("username", equalTo(user.getUsername())),
                hasProperty("password", equalTo(user.getPassword())),
                hasProperty("email", equalTo(user.getEmail())),
                hasProperty("phoneNumber", equalTo(user.getPhoneNumber())),
                hasProperty("accountBalance", equalTo(user.getAccountBalance()))
        ));
        verify(userRepository, times(1)).findById(USER_ID_1);
        verify(userRepository, times(1)).save(user);
    }

}
