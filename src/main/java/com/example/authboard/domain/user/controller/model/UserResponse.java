package com.example.authboard.domain.user.controller.model;

import com.example.authboard.domain.user.db.enums.UserRole;
import com.example.authboard.domain.user.db.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private Long id;

    private String email;

    private String nickname;

    private UserStatus status;

    private UserRole role;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime lastLoginAt;
}
