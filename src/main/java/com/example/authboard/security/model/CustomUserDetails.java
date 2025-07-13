package com.example.authboard.security.model;

import com.example.authboard.domain.user.db.UserEntity;
import com.example.authboard.domain.user.db.enums.UserRole;
import com.example.authboard.domain.user.db.enums.UserStatus;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Getter
public class CustomUserDetails implements UserDetails {

    private final Long id;
    private final String email;
    private final String password;
    private final String nickname;
    private final UserStatus status;
    private final UserRole role;
    private final OffsetDateTime createdAt;
    private final OffsetDateTime updatedAt;
    private OffsetDateTime lastLoginAt;

    public CustomUserDetails(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.email = userEntity.getEmail();
        this.password = userEntity.getPassword();
        this.nickname = userEntity.getNickname();
        this.status = userEntity.getStatus();
        this.role = userEntity.getRole();
        this.createdAt = userEntity.getCreatedAt();
        this.updatedAt = userEntity.getUpdatedAt();
        this.lastLoginAt = userEntity.getLastLoginAt();
        if (this.lastLoginAt == null) {
            this.lastLoginAt = OffsetDateTime.now();
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(() -> String.valueOf(role));
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return status.equals(UserStatus.REGISTERED);
    }

    @Override
    public boolean isAccountNonLocked() {
        return status.equals(UserStatus.REGISTERED);
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
