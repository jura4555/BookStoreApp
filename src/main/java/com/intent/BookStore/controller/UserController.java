package com.intent.BookStore.controller;

import com.intent.BookStore.dto.ChangePasswordDTO;
import com.intent.BookStore.dto.UserDTO;
import com.intent.BookStore.dto.validation.group.OnUpdate;
import com.intent.BookStore.facade.UserFacade;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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

    @GetMapping("/users/me")
    public ResponseEntity<UserDTO> getUser(Authentication authentication) {
        UserDTO user = userFacade.getUserByUsername(authentication.getName());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/users/me")
    public ResponseEntity<UserDTO> updateUser(Authentication authentication, @Validated(OnUpdate.class) @RequestBody UserDTO updatedUserDTO) {
        UserDTO updatedUser = userFacade.updateUser(authentication, updatedUserDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PatchMapping("/users/me/password")
    public ResponseEntity<UserDTO> updateUserPassword(Authentication authentication, @Valid @RequestBody ChangePasswordDTO changePasswordDTO) {
        UserDTO userDTO = userFacade.updateUserPassword(authentication, changePasswordDTO);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PatchMapping("/users/me/balance")
    public ResponseEntity<UserDTO> increaseAccountBalance(Authentication authentication, @RequestParam BigDecimal amount) {
        UserDTO userDTO = userFacade.increaseAccountBalance(authentication, amount);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PatchMapping("/users/{id}/role")
    public ResponseEntity<UserDTO> updateUserRole(@PathVariable Long id, @RequestParam String role) {
        UserDTO userDTO = userFacade.updateRole(id, role);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
}
