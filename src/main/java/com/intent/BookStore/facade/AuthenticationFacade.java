package com.intent.BookStore.facade;

import com.intent.BookStore.dto.LoginDTO;
import com.intent.BookStore.dto.UserDTO;
import com.intent.BookStore.exception.AuthenticationException;

public interface AuthenticationFacade {

    UserDTO register(UserDTO userDTO);

    UserDTO login(LoginDTO loginDTO) throws AuthenticationException;
}
