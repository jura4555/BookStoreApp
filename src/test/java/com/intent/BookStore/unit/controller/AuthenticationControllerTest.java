package com.intent.BookStore.unit.controller;

import com.intent.BookStore.controller.AuthenticationController;
import com.intent.BookStore.dto.LoginDTO;
import com.intent.BookStore.dto.UserDTO;
import com.intent.BookStore.facade.impl.AuthenticationFacadeImpl;
import com.intent.BookStore.security.jwt.JwtTokenProvider;
import com.intent.BookStore.unit.util.TestUserDataUtil;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.intent.BookStore.unit.util.TestUserDataUtil.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @Mock
    private AuthenticationFacadeImpl authenticationFacade;

    @Mock
    JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private AuthenticationController authenticationController;

    @Test
    void registerTest() {
        UserDTO initialUserDTO = TestUserDataUtil.getUserDTO1()
                .setAccountBalance(null)
                .setUsername(null);
        UserDTO userDTO = TestUserDataUtil.getUserDTO1();
        ResponseEntity<UserDTO> expectedResponseEntity = ResponseEntity.status(HttpStatus.CREATED).body(userDTO);

        when(authenticationFacade.register(initialUserDTO)).thenReturn(userDTO);

        ResponseEntity<UserDTO> resultResponseEntity = authenticationController.register(initialUserDTO);

        assertThat(resultResponseEntity.getBody(), is(expectedResponseEntity.getBody()));
        assertThat(resultResponseEntity.getStatusCode(), is(expectedResponseEntity.getStatusCode()));
        verify(authenticationFacade, times(1)).register(initialUserDTO);
    }

    @Test
    void loginTest() {
        String token = "secret";
        LoginDTO loginDTO = TestUserDataUtil.getLoginDTO1();
        UserDTO userDTO = TestUserDataUtil.getUserDTO1();
        when(authenticationFacade.login(loginDTO)).thenReturn(userDTO);
        when(jwtTokenProvider.createToken(USERNAME_1, List.of(ROLE_1.name()))).thenReturn(token);
        ResponseEntity<String> expectedResponseEntity = ResponseEntity.status(HttpStatus.OK).body(token);

        ResponseEntity<String> resultResponseEntity = authenticationController.login(loginDTO);

        assertThat(resultResponseEntity.getBody(), is(expectedResponseEntity.getBody()));
        assertThat(resultResponseEntity.getStatusCode(), is(expectedResponseEntity.getStatusCode()));
        verify(authenticationFacade, times(1)).login(loginDTO);
        verify(jwtTokenProvider, times(1)).createToken(USERNAME_1, List.of(ROLE_1.name()));
    }
}
