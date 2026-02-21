package com.anandhu.sde_dev.dto.auth;

import com.anandhu.sde_dev.common.Role;
import com.anandhu.sde_dev.model.User;
import lombok.Getter;

@Getter
public class UserResponse {
    private final String firstname;
    private final String lastname;
    private final String email;
    private final Role role;

    public UserResponse(User user) {
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.email = user.getEmail();
        this.role = user.getRole();
    }
}
