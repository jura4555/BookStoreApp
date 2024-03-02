package com.intent.BookStore.facade.impl;

import com.intent.BookStore.dto.UserDTO;
import com.intent.BookStore.facade.UserFacade;
import com.intent.BookStore.mapper.BookMapperUtil;
import com.intent.BookStore.mapper.UserMapperUtil;
import com.intent.BookStore.model.Book;
import com.intent.BookStore.model.User;
import com.intent.BookStore.service.BookService;
import com.intent.BookStore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.intent.BookStore.mapper.UserMapperUtil.toUser;
import static com.intent.BookStore.mapper.UserMapperUtil.toUserDTO;

@Component
@RequiredArgsConstructor
public class UserFacadeImpl implements UserFacade {

    private final UserService userService;

    @Override
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(UserMapperUtil::toUserDTO).collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long id) {
        return null;
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        User user = userService.getUserByUsername(username);
        return toUserDTO(user);
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User createdUser = userService.createUser(toUser(userDTO));
        return toUserDTO(createdUser);
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO updatedUserDTO) {
        return null;
    }

}
