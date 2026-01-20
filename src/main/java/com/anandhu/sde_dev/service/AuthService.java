package com.anandhu.sde_dev.service;

import com.anandhu.sde_dev.common.Role;
import com.anandhu.sde_dev.dto.auth.RegisterRequest;
import com.anandhu.sde_dev.model.User;
import com.anandhu.sde_dev.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.UnknownServiceException;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    @Transactional
    public void register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalStateException("Email already exists");
        }

        User user = User.register(
                request.getFirstname(),
                request.getLastname(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                Role.USER
        );

        userRepository.save(user);
    }

    public void authenticate(String email, String password){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }
}
