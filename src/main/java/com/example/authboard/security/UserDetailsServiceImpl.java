package com.example.authboard.security;

import com.example.authboard.common.error.ErrorCode;
import com.example.authboard.common.exception.ApiException;
import com.example.authboard.domain.user.db.enums.UserStatus;
import com.example.authboard.domain.user.service.UserService;
import com.example.authboard.security.model.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(username)
                .map(it -> userService.findUserWithThrow(username, UserStatus.REGISTERED))
                .map(User::new)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "username is null"));
    }
}
