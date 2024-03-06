package com.intent.BookStore.service;

import com.intent.BookStore.dto.ChangePasswordDTO;
import com.intent.BookStore.model.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    Page<User> getAllUsers(int pageNum, int pageSize);

    User getUserById(Long id);

    User getUserByUsername(String username);

    User createUser(User user);

    User updateUser(Long id, User updatedUser);

    User changeUserPassword(Long id, ChangePasswordDTO changePasswordDTO);

}
