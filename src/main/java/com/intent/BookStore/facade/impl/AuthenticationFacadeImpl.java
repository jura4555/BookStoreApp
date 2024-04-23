package com.intent.BookStore.facade.impl;

import com.intent.BookStore.dto.LoginDTO;
import com.intent.BookStore.dto.UserDTO;
import com.intent.BookStore.exception.AuthenticationException;
import com.intent.BookStore.facade.AuthenticationFacade;
import com.intent.BookStore.model.User;
import com.intent.BookStore.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.intent.BookStore.mapper.UserMapperUtil.toUser;
import static com.intent.BookStore.mapper.UserMapperUtil.toUserDTO;

@Component
@RequiredArgsConstructor
public class AuthenticationFacadeImpl implements AuthenticationFacade {

    private final AuthenticationService authenticationService;

    @Override
    public UserDTO register(UserDTO userDTO) {
        User createdUser = authenticationService.register(toUser(userDTO));
        return toUserDTO(createdUser);
    }

    @Override
    public UserDTO login(LoginDTO loginDTO) throws AuthenticationException {
        String login = loginDTO.getUsername();
        String password = loginDTO.getPassword();
        return toUserDTO(authenticationService.login(login, password));
    }
}
