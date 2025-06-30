package com.example.authboard.security.model;

import com.example.authboard.domain.user.db.UserEntity;
import com.example.authboard.domain.user.db.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@AllArgsConstructor
public class User implements UserDetails {

    private final UserEntity userEntity;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(() -> String.valueOf(userEntity.getRole()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return userEntity.getStatus().equals(UserStatus.REGISTERED);
    }

    @Override
    public boolean isAccountNonLocked() {
        return userEntity.getStatus().equals(UserStatus.REGISTERED);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isAccountNonLocked();

    }
}
