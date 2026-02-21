package com.anandhu.sde_dev.controller;

import com.anandhu.sde_dev.dto.auth.AuthResponse;
import com.anandhu.sde_dev.dto.auth.LoginRequest;
import com.anandhu.sde_dev.dto.auth.RegisterRequest;
import com.anandhu.sde_dev.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        String token = authService.register(request);
        return new AuthResponse(token);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        String token = authService.authenticate(request.getEmail(), request.getPassword());

        return new AuthResponse(token);
    }

    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    @org.springframework.web.bind.annotation.PutMapping("/promote/{email}")
    public org.springframework.http.ResponseEntity<String> promoteToAdmin(
            @org.springframework.web.bind.annotation.PathVariable String email) {
        authService.promoteToAdmin(email);
        return org.springframework.http.ResponseEntity.ok("User promoted to Admin successfully");
    }

    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    @org.springframework.web.bind.annotation.GetMapping("/users")
    public org.springframework.http.ResponseEntity<java.util.List<com.anandhu.sde_dev.dto.auth.UserResponse>> getAllUsers() {
        java.util.List<com.anandhu.sde_dev.dto.auth.UserResponse> users = authService.getAllUsers()
                .stream()
                .map(com.anandhu.sde_dev.dto.auth.UserResponse::new)
                .collect(java.util.stream.Collectors.toList());
        return org.springframework.http.ResponseEntity.ok(users);
    }
}
