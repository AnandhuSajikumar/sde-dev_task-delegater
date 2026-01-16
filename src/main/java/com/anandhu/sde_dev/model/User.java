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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Integer id;

    private String firstname;
    private String lastname;

    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public void promoteToAdmin(){
        this.role = Role.ADMIN;
    }

    public void changePassword(String newEncryptedPassword){
        //Pending logic
        this.password = newEncryptedPassword;
    }

    public Collection<? extends GrantedAuthority> getAuthorities(){
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    public String getUsername(){
        return email;
    }

    public boolean isAccountNonExpired(){
        return true;
    }
    public boolean isAccountNonLocked(){
        return true;
    }
    public boolean isCredentialsNonExpired(){
        return true;
    }
    public boolean isEnabled(){
        return true;
    }
}
