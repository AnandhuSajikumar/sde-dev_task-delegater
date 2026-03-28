package com.anandhu.sde_dev.service;

import com.anandhu.sde_dev.common.Role;
import com.anandhu.sde_dev.dto.auth.RegisterRequest;
import com.anandhu.sde_dev.model.User;
import com.anandhu.sde_dev.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    @Transactional
    public String register(RegisterRequest request) {
        log.info("Registering user email={}",request.getEmail());
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            log.warn("Registration failed, email already exists={}",request.getEmail());
            throw new IllegalStateException("Email already exists");
        }

        User user = User.register(
                request.getFirstname(),
                request.getLastname(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                Role.USER);

        userRepository.save(user);
        log.info("User registered successfully email={}",request.getEmail());
        return jwtService.generateToken(user);
    }

    public String authenticate(String email, String password) {
        log.debug("Authenticating user email={}", email);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        log.info("Authentication successful email={}", email);
        return jwtService.generateToken(userDetails);
    }

    @Transactional
    public void promoteToAdmin(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        user.promoteToAdmin();
        userRepository.save(user);
    }

    public java.util.List<com.anandhu.sde_dev.model.User> getAllUsers() {
        return userRepository.findAll();
    }
}
