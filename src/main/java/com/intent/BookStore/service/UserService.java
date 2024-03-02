package com.intent.BookStore.service;

import com.intent.BookStore.model.Book;
import com.intent.BookStore.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUserById(Long id);

    User getUserByUsername(String username);

    User createUser(User user);

    User updateUser(Long id, User updatedUser);

}
