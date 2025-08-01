package com.table2table.auth.controller;

import com.table2table.auth.dto.JwtResponseDto;
import com.table2table.auth.dto.LoginRequestDto;
import com.table2table.auth.dto.RegisterRequestDto;
import com.table2table.auth.dto.UserResponseDto;
import com.table2table.auth.entity.UserCred;
import com.table2table.auth.repository.UserRepository;
import com.table2table.auth.service.UserService;
import com.table2table.auth.util.UserCredUtil;
import com.table2table.security.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final WebClient.Builder webClientBuilder;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public AuthController(
            WebClient.Builder webClientBuilder, AuthenticationManager authenticationManager,
            UserService userService,
            JwtService jwtService,
            UserRepository userRepository
    ) {
        this.webClientBuilder = webClientBuilder;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequestDto request) {
        String response = userService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getEmail(),
                        loginRequestDto.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        //String token = jwtService.generateToken(loginRequest.getEmail());

        UserCred userCred = userRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow();

        String token = jwtService.generateToken(userCred.getRole(),userCred.getCredId(),userCred.getEmail());

        return ResponseEntity.ok(new JwtResponseDto(token, userCred.getEmail(), userCred.getRole()));
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        // Fetch user from database using email
        String userServiceUrl = "http://user-service/api/users/getUserByEmail/";
        UserResponseDto userDetailsResons =  webClientBuilder.build()
                .get()
                .uri(userServiceUrl + "{email}", userDetails.getUsername())
                .retrieve()
                .bodyToMono(UserResponseDto.class)
                .block();
        return ResponseEntity.ok(userDetailsResons);
    }
}

