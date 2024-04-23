package com.intent.BookStore.unit.controller;

import com.intent.BookStore.controller.UserController;
import com.intent.BookStore.dto.ChangePasswordDTO;
import com.intent.BookStore.dto.UserDTO;
import com.intent.BookStore.facade.impl.UserFacadeImpl;
import com.intent.BookStore.unit.util.TestUserDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static com.intent.BookStore.unit.util.TestUserDataUtil.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserFacadeImpl userFacadeImpl;

    @InjectMocks
    private UserController userController;

    private static final int PAGE_NUMBER_DEFAULT = 0;
    private static final int PAGE_NUMBER = 1;
    private static final int PAGE_SIZE = 2;
    private static final BigDecimal AMOUNT = BigDecimal.valueOf(100.00);

    @Test
    void getAllBooksTest() {
        UserDTO userDTO1 = TestUserDataUtil.getUserDTO1();
        UserDTO userDTO2 = TestUserDataUtil.getUserDTO2();

        Pageable pageable = PageRequest.of(PAGE_NUMBER_DEFAULT, PAGE_SIZE, Sort.by("id").ascending());
        Page<UserDTO> users = new PageImpl<>(List.of(userDTO1, userDTO2), pageable, 2);
        ResponseEntity<Page<UserDTO>> expectedResponseEntity = ResponseEntity.status(HttpStatus.OK).body(users);

        when(userFacadeImpl.getAllUsers(PAGE_NUMBER, PAGE_SIZE)).thenReturn(users);

        ResponseEntity<Page<UserDTO>> resultResponseEntity = userController.getAllUsers(PAGE_NUMBER, PAGE_SIZE);

        assertThat(resultResponseEntity.getBody(), is(expectedResponseEntity.getBody()));
        assertThat(resultResponseEntity.getStatusCode(), is(expectedResponseEntity.getStatusCode()));
        verify(userFacadeImpl).getAllUsers(PAGE_NUMBER, PAGE_SIZE);
    }

    @Test
    void getUserByIdTest() {
        UserDTO userDTO = TestUserDataUtil.getUserDTO1();
        ResponseEntity<UserDTO> expectedResponseEntity = ResponseEntity.status(HttpStatus.OK).body(userDTO);

        when(userFacadeImpl.getUserById(USER_ID_1)).thenReturn(userDTO);

        ResponseEntity<UserDTO> resultResponseEntity = userController.getUserById(USER_ID_1);

        assertThat(resultResponseEntity.getBody(), is(expectedResponseEntity.getBody()));
        assertThat(resultResponseEntity.getStatusCode(), is(expectedResponseEntity.getStatusCode()));
        verify(userFacadeImpl).getUserById(USER_ID_1);
    }

    @Test
    void getUserByUsernameTest() {
        UserDTO userDTO = TestUserDataUtil.getUserDTO1();
        ResponseEntity<UserDTO> expectedResponseEntity = ResponseEntity.status(HttpStatus.OK).body(userDTO);

        when(userFacadeImpl.getUserByUsername(USERNAME_1)).thenReturn(userDTO);

        ResponseEntity<UserDTO> resultResponseEntity = userController.getUserByUsername(USERNAME_1);

        assertThat(resultResponseEntity.getBody(), is(expectedResponseEntity.getBody()));
        assertThat(resultResponseEntity.getStatusCode(), is(expectedResponseEntity.getStatusCode()));
        verify(userFacadeImpl).getUserByUsername(USERNAME_1);
    }

    @Test
    void getUserTest() {
        UserDTO userDTO = TestUserDataUtil.getUserDTO1();
        Authentication authentication = new UsernamePasswordAuthenticationToken(USERNAME_1, "",
                Collections.singleton(new SimpleGrantedAuthority(ROLE_1.name())));
        ResponseEntity<UserDTO> expectedResponseEntity = ResponseEntity.status(HttpStatus.OK).body(userDTO);

        when(userFacadeImpl.getUserByUsername(USERNAME_1)).thenReturn(userDTO);

        ResponseEntity<UserDTO> resultResponseEntity = userController.getUser(authentication);

        assertThat(resultResponseEntity.getBody(), is(expectedResponseEntity.getBody()));
        assertThat(resultResponseEntity.getStatusCode(), is(expectedResponseEntity.getStatusCode()));
        verify(userFacadeImpl).getUserByUsername(USERNAME_1);
    }

//    @Test
//    void createUserTest() {
//        UserDTO userDTO = TestUserDataUtil.getUserDTO1().setId(0L);
//        UserDTO createdUserDTO = TestUserDataUtil.getUserDTO1();
//        ResponseEntity<UserDTO> expectedResponseEntity = ResponseEntity.status(HttpStatus.CREATED).body(createdUserDTO);
//
//        when(userFacadeImpl.createUser(userDTO)).thenReturn(createdUserDTO);
//
//        ResponseEntity<UserDTO> resultResponseEntity = userController.createUser(userDTO);
//
//        assertThat(resultResponseEntity.getBody(), is(expectedResponseEntity.getBody()));
//        assertThat(resultResponseEntity.getStatusCode(), is(expectedResponseEntity.getStatusCode()));
//        verify(userFacadeImpl).createUser(userDTO);
//    }

    @Test
    void updateUserTest() {
        UserDTO userDTO = TestUserDataUtil.getUserDTO1().setId(0L);
        UserDTO updatedUserDTO = TestUserDataUtil.getUserDTO1();
        Authentication authentication = new UsernamePasswordAuthenticationToken(USERNAME_1, "",
                Collections.singleton(new SimpleGrantedAuthority(ROLE_1.name())));
        ResponseEntity<UserDTO> expectedResponseEntity = ResponseEntity.status(HttpStatus.OK).body(updatedUserDTO);

        when(userFacadeImpl.updateUser(authentication, userDTO)).thenReturn(updatedUserDTO);

        ResponseEntity<UserDTO> resultResponseEntity = userController.updateUser(authentication, userDTO);

        assertThat(resultResponseEntity.getBody(), is(expectedResponseEntity.getBody()));
        assertThat(resultResponseEntity.getStatusCode(), is(expectedResponseEntity.getStatusCode()));
        verify(userFacadeImpl).updateUser(authentication, userDTO);
    }

    @Test
    void updateUserPasswordTest() {
        UserDTO userDTO = TestUserDataUtil.getUserDTO1().setPassword(PASSWORD_NEW);
        ChangePasswordDTO changePasswordDTO = TestUserDataUtil.getChangePasswordDTO();
        Authentication authentication = new UsernamePasswordAuthenticationToken(USERNAME_1, "",
                Collections.singleton(new SimpleGrantedAuthority(ROLE_1.name())));
        ResponseEntity<UserDTO> expectedResponseEntity = ResponseEntity.status(HttpStatus.OK).body(userDTO);

        when(userFacadeImpl.updateUserPassword(authentication, changePasswordDTO)).thenReturn(userDTO);

        ResponseEntity<UserDTO> resultResponseEntity = userController.updateUserPassword(authentication, changePasswordDTO);

        assertThat(resultResponseEntity.getBody(), is(expectedResponseEntity.getBody()));
        assertThat(resultResponseEntity.getStatusCode(), is(expectedResponseEntity.getStatusCode()));
        verify(userFacadeImpl).updateUserPassword(authentication, changePasswordDTO);
    }

    @Test
    void increaseAccountBalanceTest() {
        UserDTO userDTO = TestUserDataUtil.getUserDTO1();
        Authentication authentication = new UsernamePasswordAuthenticationToken(USERNAME_1, "",
                Collections.singleton(new SimpleGrantedAuthority(ROLE_1.name())));
        ResponseEntity<UserDTO> expectedResponseEntity = ResponseEntity.status(HttpStatus.OK).body(userDTO);

        when(userFacadeImpl.increaseAccountBalance(authentication, AMOUNT)).thenReturn(userDTO);

        ResponseEntity<UserDTO> resultResponseEntity = userController.increaseAccountBalance(authentication, AMOUNT);

        assertThat(resultResponseEntity.getBody(), is(expectedResponseEntity.getBody()));
        assertThat(resultResponseEntity.getStatusCode(), is(expectedResponseEntity.getStatusCode()));
        verify(userFacadeImpl).increaseAccountBalance(authentication, AMOUNT);
    }

    @Test
    void updateUserRoleTest() {
        UserDTO userDTO = TestUserDataUtil.getUserDTO1();
        ResponseEntity<UserDTO> expectedResponseEntity = ResponseEntity.status(HttpStatus.OK).body(userDTO);

        when(userFacadeImpl.updateRole(USER_ID_1, ROLE_1.name())).thenReturn(userDTO);

        ResponseEntity<UserDTO> resultResponseEntity = userController.updateUserRole(USER_ID_1, ROLE_1.name());

        assertThat(resultResponseEntity.getBody(), is(expectedResponseEntity.getBody()));
        assertThat(resultResponseEntity.getStatusCode(), is(expectedResponseEntity.getStatusCode()));
        verify(userFacadeImpl).updateRole(USER_ID_1, ROLE_1.name());
    }


}
