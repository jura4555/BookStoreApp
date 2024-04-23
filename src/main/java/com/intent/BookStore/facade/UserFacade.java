package com.intent.BookStore.facade;

import com.intent.BookStore.dto.ChangePasswordDTO;
import com.intent.BookStore.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

import java.math.BigDecimal;


public interface UserFacade {

    Page<UserDTO> getAllUsers(int pageNum, int pageSize);

    UserDTO getUserById(Long id);

    UserDTO getUserByUsername(String username);

    UserDTO updateUser(Authentication authentication, UserDTO updatedUserDTO);

    UserDTO updateUserPassword(Authentication authentication, ChangePasswordDTO changePasswordDTO);

    UserDTO increaseAccountBalance(Authentication authentication, BigDecimal amount);

    UserDTO updateRole(Long id, String role);
}
