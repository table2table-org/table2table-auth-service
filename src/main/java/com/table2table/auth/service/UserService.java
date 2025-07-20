package com.table2table.auth.service;


import com.table2table.auth.dto.LoginRequestDto;
import com.table2table.auth.dto.RegisterRequestDto;
import com.table2table.auth.dto.UserResponseDto;
import com.table2table.auth.entity.UserCred;
import com.table2table.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    private final WebClient.Builder webClientBuilder;

    public UserService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public String register(RegisterRequestDto request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        UserCred userCred = new UserCred();
        userCred.setName(request.getName());
        userCred.setEmail(request.getEmail());
        userCred.setPassword(passwordEncoder.encode(request.getPassword()));
        userCred.setLocked(false);
        userCred.setRole(request.getRole());
        userCred.setCreatedOn(LocalDateTime.now());

        userRepository.save(userCred);

        Long credId = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"))
                .getCredId();

        request.setCredId(credId);
        request.setCreatedOn(LocalDateTime.now());
        request.setLastUpdatedOn(LocalDateTime.now());

        String userServiceUrl = "http://user-service/api/users/register";
        webClientBuilder.build()
                .post()
                .uri(userServiceUrl)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(UserResponseDto.class)
                .block();


        return "User registered successfully";
    }

    public ResponseEntity<String> login(LoginRequestDto request) {
            Optional<UserCred> userOptional = userRepository.findByEmail(request.getEmail());

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password.");
        }

        UserCred userCred = userOptional.get();
        if (!passwordEncoder.matches(request.getPassword(), userCred.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password.");
        }

        return ResponseEntity.ok("Login successful.");
    }



}
