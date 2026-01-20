package com.anandhu.sde_dev.dto.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class LoginRequest {
    private String email;
    private String password;
}
