package com.soas.apigateway.service;

import com.soas.api.dto.UserDTO;  // ← IZ API-DEPENDENCY!
import com.soas.apigateway.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Service
@Slf4j
public class CustomUserDetailsService implements ReactiveUserDetailsService {

    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Mono<UserDetails> findByUsername(String email) {
        log.info("Authenticating user: {}", email);

        return userService.getUserByEmail(email)
                .map(this::convertToUserDetails)
                .switchIfEmpty(Mono.error(new RuntimeException("User not found: " + email)));
    }

    private UserDetails convertToUserDetails(UserDTO userDTO) {
        return User.builder()
                .username(userDTO.getEmail())
                .password("{noop}" + userDTO.getPassword())
                .authorities(Collections.singletonList(
                        new SimpleGrantedAuthority("ROLE_" + userDTO.getRole().name())  // ← .name() jer je ENUM!
                ))
                .build();
    }
}
