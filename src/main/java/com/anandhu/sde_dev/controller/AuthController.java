package com.anandhu.sde_dev.controller;

import com.anandhu.sde_dev.dto.auth.AuthResponse;
import com.anandhu.sde_dev.dto.auth.LoginRequest;
import com.anandhu.sde_dev.dto.auth.RegisterRequest;
import com.anandhu.sde_dev.dto.auth.UserResponse;
import com.anandhu.sde_dev.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        log.info("SignUP Attempt={}", request.getEmail());
        String token = authService.register(request);
        log.info("Signup success={}", request.getEmail());
        return new AuthResponse(token);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        log.info("Login attempt email={}", request.getEmail());

        String token = authService.authenticate(request.getEmail(), request.getPassword());

        log.info("Login success email={}", request.getEmail());

        return new AuthResponse(token);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/promote/{email}")
    public org.springframework.http.ResponseEntity<String> promoteToAdmin(
            @PathVariable String email) {
        authService.promoteToAdmin(email);
        return org.springframework.http.ResponseEntity.ok("User promoted to Admin successfully");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = authService.getAllUsers()
                .stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
        return org.springframework.http.ResponseEntity.ok(users);
    }
}
