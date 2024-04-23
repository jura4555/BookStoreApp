package com.intent.BookStore.unit.security;

import com.intent.BookStore.exception.AuthenticationException;
import com.intent.BookStore.exception.UserNotFoundException;
import com.intent.BookStore.model.User;
import com.intent.BookStore.repository.UserRepository;
import com.intent.BookStore.security.impl.AuthenticationServiceImpl;
import com.intent.BookStore.unit.util.TestUserDataUtil;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.intent.BookStore.unit.util.TestUserDataUtil.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Test
    void registerTest() {
        User user = TestUserDataUtil.getUser1()
                .setAccountBalance(null)
                .setUsername(null);
        User createdUser = TestUserDataUtil.getUser1();
        when(userRepository.save(user)).thenReturn(createdUser);
        User result = authenticationService.register(user);
        assertThat(result, allOf(
                hasProperty("id", equalTo(createdUser.getId())),
                hasProperty("username", equalTo(createdUser.getUsername())),
                hasProperty("password", equalTo(createdUser.getPassword())),
                hasProperty("email", equalTo(createdUser.getEmail())),
                hasProperty("phoneNumber", equalTo(createdUser.getPhoneNumber())),
                hasProperty("accountBalance", equalTo(createdUser.getAccountBalance())),
                hasProperty("role", equalTo(createdUser.getRole()))
        ));
        verify(userRepository, times(1)).save(user);
    }


    @Test
    void loginTest() {
        User user = TestUserDataUtil.getUser1();
        when(userRepository.findByUsername(USERNAME_1)).thenReturn(Optional.of(user));
        User result = authenticationService.login(USERNAME_1, PASSWORD_1);
        assertThat(result, allOf(
                hasProperty("id", equalTo(user.getId())),
                hasProperty("username", equalTo(user.getUsername())),
                hasProperty("password", equalTo(user.getPassword())),
                hasProperty("email", equalTo(user.getEmail())),
                hasProperty("phoneNumber", equalTo(user.getPhoneNumber())),
                hasProperty("accountBalance", equalTo(user.getAccountBalance())),
                hasProperty("role", equalTo(user.getRole()))
        ));
        verify(userRepository, times(1)).findByUsername(USERNAME_1);
    }

    @Test
    void loginWithAuthenticationExceptionTest() {
        User user = TestUserDataUtil.getUser1();
        when(userRepository.findByUsername(USERNAME_1)).thenReturn(Optional.of(user));
        assertThrows(AuthenticationException.class, () -> authenticationService.login(USERNAME_1, PASSWORD_2));

    }
}
