package com.soas.apigateway.service;

import com.soas.api.dto.UserDTO;  // ← IZ API-DEPENDENCY!
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final WebClient.Builder webClientBuilder;

    public Mono<UserDTO> getUserByEmail(String email) {
        return webClientBuilder.build()
                .get()
                .uri("http://users-service/users/email/{email}", email)
                .retrieve()
                .bodyToMono(UserDTO.class)
                .doOnError(error -> log.error("Error fetching user: {}", email, error))
                .onErrorResume(error -> Mono.empty());
    }
}
