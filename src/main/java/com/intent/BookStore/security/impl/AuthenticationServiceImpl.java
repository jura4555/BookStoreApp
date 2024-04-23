package com.intent.BookStore.security.impl;

import com.intent.BookStore.dto.UserDTO;
import com.intent.BookStore.exception.AuthenticationException;
import com.intent.BookStore.exception.PasswordMismatchException;
import com.intent.BookStore.model.User;
import com.intent.BookStore.repository.UserRepository;
import com.intent.BookStore.security.AuthenticationService;
import com.intent.BookStore.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

import static com.intent.BookStore.util.ExceptionMessageUtil.AUTHENTICATION_EXCEPTION;
import static com.intent.BookStore.util.ExceptionMessageUtil.PASSWORD_MISMATCH_ERROR_MESSAGE;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public User register(User user) {
        user.setAccountBalance(BigDecimal.ZERO);
        user.setRole(User.Role.USER);
        return userRepository.save(user);
    }

    @Override
    public User login(String login, String password) throws AuthenticationException {
        Optional<User> user = userRepository.findByUsername(login);
        if(user.isPresent() && user.get().getPassword().equals(password)) {
            return user.get();
        }
       throw new AuthenticationException(AUTHENTICATION_EXCEPTION);
    }
}
