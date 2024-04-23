package com.intent.BookStore.unit.security;

import com.intent.BookStore.model.User;
import com.intent.BookStore.security.CustomUserDetailsService;
import com.intent.BookStore.service.UserService;
import com.intent.BookStore.unit.util.TestUserDataUtil;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import static com.intent.BookStore.unit.util.TestUserDataUtil.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void loadUserByUsernameTest() {
        User user = TestUserDataUtil.getUser1();
        when(userService.getUserByUsername(USERNAME_1)).thenReturn(user);
        UserDetails result = customUserDetailsService.loadUserByUsername(USERNAME_1);
        assertThat(result, allOf(
                hasProperty("username", equalTo(user.getUsername())),
                hasProperty("password", equalTo(user.getPassword())),
                hasProperty("authorities", contains(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())))
                ));
        verify(userService, times(1)).getUserByUsername(USERNAME_1);
    }

}
