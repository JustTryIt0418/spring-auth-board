package com.example.authboard.domain.admin.controller.model;

import com.example.authboard.domain.user.db.enums.UserRole;
import com.example.authboard.domain.user.db.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleRequest {

    private Long userId;

    private UserRole userRole;
}
