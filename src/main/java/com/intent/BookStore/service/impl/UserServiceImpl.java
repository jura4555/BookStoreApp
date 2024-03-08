package com.intent.BookStore.service.impl;

import com.intent.BookStore.dto.ChangePasswordDTO;
import com.intent.BookStore.exception.IncorrectPasswordException;
import com.intent.BookStore.exception.PasswordMismatchException;
import com.intent.BookStore.exception.UserNotFoundException;
import com.intent.BookStore.model.User;
import com.intent.BookStore.repository.UserRepository;
import com.intent.BookStore.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.intent.BookStore.util.ExceptionMessageUtil.*;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public Page<User> getAllUsers(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,
                Sort.by("id"));
        return userRepository.findAll(pageable);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND_BY_ID_ERROR_MESSAGE, id)));
    }

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND_BY_USERNAME_ERROR_MESSAGE, username)));
    }

    @Override
    @Transactional
    public User createUser(User user) {
        user.setAccountBalance(BigDecimal.ZERO);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User updateUser(Long id, User updatedUser) {
        User existUser = getUserById(id);
        updatedUser.setId(id);
        updatedUser.setPassword(existUser.getPassword());
        return userRepository.save(updatedUser);
    }

    @Override
    @Transactional
    public User changeUserPassword(Long id, ChangePasswordDTO changePasswordDTO) {
        User existUser = getUserById(id);
        String newPassword = changePasswordDTO.getNewPassword();
        checkExistPassword(changePasswordDTO.getCurrentPassword(), existUser.getPassword());
        checkConfirmPassword(newPassword, changePasswordDTO.getConfirmPassword());
        existUser.setPassword(newPassword);
        return userRepository.save(existUser);
    }

    @Override
    public User increaseAccountBalance(Long id, BigDecimal amount) {
        User existUser = getUserById(id);
        existUser.setAccountBalance(existUser.getAccountBalance().add(amount));
        return userRepository.save(existUser);
    }

    private void checkConfirmPassword(String newPassword, String confirmPassword) {
        if(! newPassword.equals(confirmPassword)){
            throw new PasswordMismatchException(PASSWORD_MISMATCH_ERROR_MESSAGE);
        }
    }

    private void checkExistPassword(String currentPassword, String existPassword) {
        if (! currentPassword.equals(existPassword)) {
            throw new IncorrectPasswordException(INCORRECT_PASSWORD_ERROR_MESSAGE);
        }
    }

}
