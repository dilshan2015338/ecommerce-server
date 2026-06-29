package com.dilshan.ecommerce.controller;

import com.dilshan.ecommerce.data.User;
import com.dilshan.ecommerce.dto.AuthResponse;
import com.dilshan.ecommerce.dto.LoginRequest;
import com.dilshan.ecommerce.dto.RegisterRequest;
import com.dilshan.ecommerce.repo.UserRepository;
import com.dilshan.ecommerce.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;

    // POST /api/auth/register
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            AuthResponse response = authService.register(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // POST /api/auth/login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            AuthResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<com.dilshan.ecommerce.data.User> getProfile(@RequestHeader("Authorization") String token) {
        Optional<User> user = userRepository.findByEmail("malindadilshan1000@gmail.com");
        return ResponseEntity.ok(user.get());
    }
}

