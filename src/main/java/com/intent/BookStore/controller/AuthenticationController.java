package com.intent.BookStore.controller;

import com.intent.BookStore.dto.LoginDTO;
import com.intent.BookStore.dto.UserDTO;
import com.intent.BookStore.dto.validation.group.OnCreate;
import com.intent.BookStore.facade.AuthenticationFacade;
import com.intent.BookStore.security.jwt.JwtTokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationFacade authenticationFacade;

    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@Validated(OnCreate.class) @RequestBody UserDTO userDTO) {
        UserDTO createdUser = authenticationFacade.register(userDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginDTO loginDTO) {
        UserDTO userDTO = authenticationFacade.login(loginDTO);
        String token = jwtTokenProvider.createToken(userDTO.getUsername(), List.of(userDTO.getRole().name()));
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
