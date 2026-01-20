package com.anandhu.sde_dev.model;

import ch.qos.logback.core.model.INamedModel;
import com.anandhu.sde_dev.common.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User(String firstname, String lastname, String email, String password, Role role) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public void promoteToAdmin(){
        this.role = Role.ADMIN;
    }

    public void changePassword(String newEncryptedPassword){
        //Pending logic
        this.password = newEncryptedPassword;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername(){
        return email;
    }

    @Override
    public boolean isAccountNonExpired(){
        return true;
    }
    @Override
    public boolean isAccountNonLocked(){
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }
    @Override
    public boolean isEnabled(){
        return true;
    }


    public static User register(
            String firstname,
            String lastname,
            String email,
            String encodedPassword,
            Role role
    ){
        User user = new User();
        user.firstname = firstname;
        user.lastname = lastname;
        user.email = email;
        user.password = encodedPassword;
        user.role = role;
        return user;

    }

}
