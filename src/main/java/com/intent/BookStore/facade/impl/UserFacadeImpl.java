package com.intent.BookStore.facade.impl;

import com.intent.BookStore.dto.ChangePasswordDTO;
import com.intent.BookStore.dto.UserDTO;
import com.intent.BookStore.facade.UserFacade;
import com.intent.BookStore.mapper.UserMapperUtil;
import com.intent.BookStore.model.User;
import com.intent.BookStore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
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
    public UserDTO updateUser(Authentication authentication, UserDTO updatedUserDTO) {
        Long userId = userService.getUserByUsername(authentication.getName()).getId();
        return toUserDTO(userService.updateUser(userId, toUser(updatedUserDTO)));
    }

    @Override
    public UserDTO updateUserPassword(Authentication authentication, ChangePasswordDTO changePasswordDTO) {
        Long userId = userService.getUserByUsername(authentication.getName()).getId();
        return toUserDTO(userService.changeUserPassword(userId, changePasswordDTO));
    }

    @Override
    public UserDTO increaseAccountBalance(Authentication authentication, BigDecimal amount) {
        Long userId = userService.getUserByUsername(authentication.getName()).getId();
        return toUserDTO(userService.increaseAccountBalance(userId, amount));
    }

    @Override
    public UserDTO updateRole(Long id, String role) {
        return toUserDTO(userService.updateRole(id, role));
    }
}
