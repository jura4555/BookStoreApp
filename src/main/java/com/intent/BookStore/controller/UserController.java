package com.intent.BookStore.controller;

import com.intent.BookStore.dto.ChangePasswordDTO;
import com.intent.BookStore.dto.UserDTO;
import com.intent.BookStore.dto.validation.group.OnCreate;
import com.intent.BookStore.dto.validation.group.OnUpdate;
import com.intent.BookStore.facade.UserFacade;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserFacade userFacade;

    @GetMapping("/users")
    public ResponseEntity<Page<UserDTO>> getAllUsers(
            @RequestParam(defaultValue = "1") @Min(value = 1, message = "page should be greater or equals one")
            int pageNum,
            @RequestParam(defaultValue = "1") @Min(value = 1, message = "page should be greater or equals one")
            int pageSize) {
        Page<UserDTO> users = userFacade.getAllUsers(pageNum, pageSize);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/users/name/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        UserDTO user = userFacade.getUserByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO user = userFacade.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<UserDTO> createUser(@Validated(OnCreate.class) @RequestBody UserDTO userDTO) {
        UserDTO createdUser = userFacade.createUser(userDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Validated(OnUpdate.class) @RequestBody UserDTO updatedUserDTO) {
        UserDTO updatedUser = userFacade.updateUser(id, updatedUserDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<UserDTO> updateUserPassword(@PathVariable Long id, @Valid @RequestBody ChangePasswordDTO changePasswordDTO) {
        UserDTO userDTO = userFacade.updateUserPassword(id, changePasswordDTO);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PatchMapping("/users/{id}/increase-balance")
    public ResponseEntity<UserDTO> increaseAccountBalance(@PathVariable Long id, @RequestParam BigDecimal amount) {
        UserDTO userDTO = userFacade.increaseAccountBalance(id, amount);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

}
