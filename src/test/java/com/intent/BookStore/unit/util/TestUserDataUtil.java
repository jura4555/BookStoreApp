package com.intent.BookStore.unit.util;

import com.intent.BookStore.dto.ChangePasswordDTO;
import com.intent.BookStore.dto.LoginDTO;
import com.intent.BookStore.dto.UserDTO;
import com.intent.BookStore.model.User;

import java.math.BigDecimal;


public class TestUserDataUtil {

    public static final long USER_ID_1 = 1L;
    public static final String USERNAME_1 = "alex_jones";
    public static final String PASSWORD_1 = "password1234";
    public static final String EMAIL_1 = "alex.jones@example.com";
    public static final String PHONE_NUMBER_1 = "+380685214759";
    public static final BigDecimal ACCOUNT_BALANCE_1 = BigDecimal.valueOf(100);

    public static final User.Role ROLE_1 = User.Role.ADMIN;

    public static final String PASSWORD_NEW = "newPassword1452";

    public static final long USER_ID_2 = 2L;
    public static final String USERNAME_2 = "alice_jonson";
    public static final String PASSWORD_2 = "jonson2004";
    public static final String EMAIL_2 = "alice.jonson@example.com";
    public static final String PHONE_NUMBER_2 = "+380685244771";
    public static final BigDecimal ACCOUNT_BALANCE_2 = BigDecimal.valueOf(200);

    public static final User.Role ROLE_2 = User.Role.MANAGER;


    public static User getUser1() {
        return new User()
                .setId(USER_ID_1)
                .setUsername(USERNAME_1)
                .setPassword(PASSWORD_1)
                .setEmail(EMAIL_1)
                .setPhoneNumber(PHONE_NUMBER_1)
                .setAccountBalance(ACCOUNT_BALANCE_1)
                .setRole(ROLE_1);
    }

    public static User getUser2() {
        return new User()
                .setId(USER_ID_2)
                .setUsername(USERNAME_2)
                .setPassword(PASSWORD_2)
                .setEmail(EMAIL_2)
                .setPhoneNumber(PHONE_NUMBER_2)
                .setAccountBalance(ACCOUNT_BALANCE_2)
                .setRole(ROLE_2);
    }

    public static UserDTO getUserDTO1() {
        return new UserDTO()
                .setId(USER_ID_1)
                .setUsername(USERNAME_1)
                .setPassword(PASSWORD_1)
                .setEmail(EMAIL_1)
                .setPhoneNumber(PHONE_NUMBER_1)
                .setAccountBalance(ACCOUNT_BALANCE_1)
                .setRole(ROLE_1);
    }

    public static UserDTO getUserDTO2() {
        return new UserDTO()
                .setId(USER_ID_2)
                .setUsername(USERNAME_2)
                .setPassword(PASSWORD_2)
                .setEmail(EMAIL_2)
                .setPhoneNumber(PHONE_NUMBER_2)
                .setAccountBalance(ACCOUNT_BALANCE_2)
                .setRole(ROLE_2);
    }

    public static ChangePasswordDTO getChangePasswordDTO() {
        return new ChangePasswordDTO()
                .setCurrentPassword(PASSWORD_1)
                .setNewPassword(PASSWORD_NEW)
                .setConfirmPassword(PASSWORD_NEW);
    }

    public static LoginDTO getLoginDTO1() {
        return new LoginDTO()
                .setUsername(USERNAME_1)
                .setPassword(PASSWORD_1);
    }

}
