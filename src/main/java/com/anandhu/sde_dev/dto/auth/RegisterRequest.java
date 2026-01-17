package com.anandhu.sde_dev.dto.auth;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor

public class RegisterRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String password;

}
