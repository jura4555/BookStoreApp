package com.intent.BookStore.config;

import com.intent.BookStore.model.User;
import com.intent.BookStore.security.jwt.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String ADMIN = User.Role.ADMIN.name();
    private static final String MANAGER = User.Role.MANAGER.name();
    private static final String USER = User.Role.USER.name();
    private final UserDetailsService userDetailsService;
    private final JwtTokenFilter jwtTokenFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .userDetailsService(userDetailsService)
                .authorizeHttpRequests(auth -> auth.requestMatchers(
                            HttpMethod.POST, "/register", "/login").permitAll()
                        .requestMatchers("/swagger-ui.html").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users/me").hasAnyRole(ADMIN, MANAGER, USER)
                        .requestMatchers(HttpMethod.GET, "/users", "/users/{id}", "/users/name/{username}")
                                .hasAnyRole(ADMIN, MANAGER)
                        .requestMatchers(HttpMethod.PUT, "/users/me").hasAnyRole(ADMIN, MANAGER, USER)
                        .requestMatchers(HttpMethod.PATCH, "/users/me/password", "/users/me/balance")
                                .hasAnyRole(ADMIN, MANAGER, USER)
                        .requestMatchers(HttpMethod.PATCH, "/users/{id}/role").hasRole(ADMIN)
                        .requestMatchers(HttpMethod.GET, "/books", "/books/{id}", "/books/title/{title}", "/books/search")
                                .permitAll()
                        .requestMatchers(HttpMethod.POST, "/books").hasAnyRole(ADMIN, MANAGER)
                        .requestMatchers(HttpMethod.PUT, "/books/{id}").hasAnyRole(ADMIN, MANAGER)
                        .requestMatchers(HttpMethod.GET, "/orders/{id}").hasAnyRole(ADMIN, MANAGER)
                        .anyRequest()
                        .authenticated()
                )
                .httpBasic(AbstractHttpConfigurer::disable)
                .headers(h -> h.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000/",
                "http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT",
                "PATCH", "DELETE",  "OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control",
                "Content-Type", "Origin"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
