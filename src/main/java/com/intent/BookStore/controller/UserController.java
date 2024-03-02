package com.intent.BookStore.controller;

import com.intent.BookStore.dto.BookDTO;
import com.intent.BookStore.dto.UserDTO;
import com.intent.BookStore.dto.validation.group.OnCreate;
import com.intent.BookStore.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserFacade userFacade;

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userFacade.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/users/name/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        UserDTO user = userFacade.getUserByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @PostMapping("/users")
    public ResponseEntity<UserDTO> createUser(@Validated(OnCreate.class) @RequestBody UserDTO userDTO) {
        UserDTO createdUser = userFacade.createUser(userDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }





}
