package com.intent.BookStore.security;


import com.intent.BookStore.dto.UserDTO;
import com.intent.BookStore.exception.AuthenticationException;
import com.intent.BookStore.model.User;

public interface AuthenticationService {
    User register(User user);

    User login(String login, String password) throws AuthenticationException;
}
