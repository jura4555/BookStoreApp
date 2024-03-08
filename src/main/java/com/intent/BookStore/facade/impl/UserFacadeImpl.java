package com.intent.BookStore.facade.impl;

import com.intent.BookStore.dto.ChangePasswordDTO;
import com.intent.BookStore.dto.UserDTO;
import com.intent.BookStore.facade.UserFacade;
import com.intent.BookStore.mapper.UserMapperUtil;
import com.intent.BookStore.model.User;
import com.intent.BookStore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static com.intent.BookStore.mapper.UserMapperUtil.toUser;
import static com.intent.BookStore.mapper.UserMapperUtil.toUserDTO;

@Component
@RequiredArgsConstructor
public class UserFacadeImpl implements UserFacade {

    private final UserService userService;

    @Override
    public Page<UserDTO> getAllUsers(int pageNum, int pageSize) {
        Page<User> userPage = userService.getAllUsers(pageNum, pageSize);
        return userPage.map(UserMapperUtil::toUserDTO);
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userService.getUserById(id);
        return toUserDTO(user);
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
        User undatedUser = userService.updateUser(id, toUser(updatedUserDTO));
        return toUserDTO(undatedUser);
    }

    @Override
    public UserDTO updateUserPassword(Long id, ChangePasswordDTO changePasswordDTO) {
        User undatedUser = userService.changeUserPassword(id, changePasswordDTO);
        return toUserDTO(undatedUser);
    }

    @Override
    public UserDTO increaseAccountBalance(Long id, BigDecimal amount) {
        return toUserDTO(userService.increaseAccountBalance(id, amount));
    }
}
