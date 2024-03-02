package com.intent.BookStore.facade;

import com.intent.BookStore.dto.UserDTO;

import java.util.List;

public interface UserFacade {

    List<UserDTO> getAllUsers();

    UserDTO getUserById(Long id);

    UserDTO getUserByUsername(String username);


    UserDTO createUser(UserDTO userDTO);

    UserDTO updateUser(Long id, UserDTO updatedUserDTO);

}
