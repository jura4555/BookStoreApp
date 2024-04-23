package com.intent.BookStore.unit.facade;

import com.intent.BookStore.dto.LoginDTO;
import com.intent.BookStore.dto.UserDTO;
import com.intent.BookStore.facade.impl.AuthenticationFacadeImpl;
import com.intent.BookStore.model.User;
import com.intent.BookStore.security.AuthenticationService;
import com.intent.BookStore.unit.util.TestUserDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class AuthenticationFacadeImplTest {

    @Mock
    AuthenticationService authenticationService;

    @InjectMocks
    AuthenticationFacadeImpl authenticationFacade;

    @Test
    void registerTest() {
        UserDTO initialUserDTO = TestUserDataUtil.getUserDTO1()
                .setAccountBalance(null)
                .setUsername(null);
        User user = TestUserDataUtil.getUser1();
        when(authenticationService.register(any(User.class))).thenReturn(user);
        UserDTO result = authenticationFacade.register(initialUserDTO);
        assertThat(result, allOf(
                hasProperty("id", equalTo(user.getId())),
                hasProperty("username", equalTo(user.getUsername())),
                hasProperty("password", equalTo(user.getPassword())),
                hasProperty("email", equalTo(user.getEmail())),
                hasProperty("phoneNumber", equalTo(user.getPhoneNumber())),
                hasProperty("accountBalance", equalTo(user.getAccountBalance())),
                hasProperty("role", equalTo(user.getRole()))
        ));
        verify(authenticationService, times(1)).register(any(User.class));
    }


    @Test
    void loginTest() {
        LoginDTO loginDTO = TestUserDataUtil.getLoginDTO1();
        User user = TestUserDataUtil.getUser1();
        when(authenticationService.login(user.getUsername(), user.getPassword())).thenReturn(user);
        UserDTO result = authenticationFacade.login(loginDTO);
        assertThat(result, allOf(
                hasProperty("id", equalTo(user.getId())),
                hasProperty("username", equalTo(user.getUsername())),
                hasProperty("password", equalTo(user.getPassword())),
                hasProperty("email", equalTo(user.getEmail())),
                hasProperty("phoneNumber", equalTo(user.getPhoneNumber())),
                hasProperty("accountBalance", equalTo(user.getAccountBalance())),
                hasProperty("role", equalTo(user.getRole()))
        ));
        verify(authenticationService, times(1)).login(user.getUsername(), user.getPassword());
    }


}
