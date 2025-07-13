package com.example.authboard.security;

import com.example.authboard.common.error.UserErrorCode;
import com.example.authboard.common.exception.ApiException;
import com.example.authboard.domain.user.db.enums.UserStatus;
import com.example.authboard.domain.user.service.UserService;
import com.example.authboard.security.model.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserContext {

    private final UserService userService;

    public CustomUserDetails getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ApiException(UserErrorCode.USER_NOT_FOUND);
        }

        if (authentication.getPrincipal() instanceof String email) {
            return new CustomUserDetails(userService.findUserWithThrow(email, UserStatus.REGISTERED));
        } else {
            return (CustomUserDetails) authentication.getPrincipal();
        }
    }
}
