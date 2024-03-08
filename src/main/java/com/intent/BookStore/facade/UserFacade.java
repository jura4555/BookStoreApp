package com.intent.BookStore.facade;

import com.intent.BookStore.dto.ChangePasswordDTO;
import com.intent.BookStore.dto.UserDTO;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;


public interface UserFacade {

    Page<UserDTO> getAllUsers(int pageNum, int pageSize);

    UserDTO getUserById(Long id);

    UserDTO getUserByUsername(String username);

    UserDTO createUser(UserDTO userDTO);

    UserDTO updateUser(Long id, UserDTO updatedUserDTO);

    UserDTO updateUserPassword(Long id, ChangePasswordDTO changePasswordDTO);

    UserDTO increaseAccountBalance(Long id, BigDecimal amount);

}
