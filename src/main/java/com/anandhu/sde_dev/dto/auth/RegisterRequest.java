package com.anandhu.sde_dev.dto.auth;
import com.anandhu.sde_dev.common.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor

public class RegisterRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Role role;

}
