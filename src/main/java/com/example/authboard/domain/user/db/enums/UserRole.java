package com.example.authboard.domain.user.db.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserRole {

    ROLE_USER("유저"),
    ROLE_ADMIN("관리자");

    private String description;
}
