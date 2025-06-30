package com.example.authboard.domain.user.service;

import com.example.authboard.common.error.ErrorCode;
import com.example.authboard.common.error.UserErrorCode;
import com.example.authboard.common.exception.ApiException;
import com.example.authboard.domain.user.controller.model.UserListRequest;
import com.example.authboard.domain.user.controller.model.UserResponse;
import com.example.authboard.domain.user.db.UserEntity;
import com.example.authboard.domain.user.db.UserRepository;
import com.example.authboard.domain.user.db.enums.UserRole;
import com.example.authboard.domain.user.db.enums.UserStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 사용자 저장
    public UserEntity saveUser(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    // 사용자 조회
    @Cacheable(value = "user", key = "#email")
    public UserEntity findUserWithThrow (
            String email,
            UserStatus status
    ) {
        return userRepository.findFirstByEmailAndStatusOrderByIdDesc(email, status)
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
    }

    public UserEntity findUserWithThrow (
            Long userId,
            UserStatus status
    ) {
        return userRepository.findFirstByIdAndStatusOrderByIdDesc(userId, status)
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
    }

    // 사용자 목록 조회
    public Page<UserResponse> getUserList(
            @ParameterObject UserListRequest request,
            @ParameterObject
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return userRepository.findAllUsers(request, pageable);
    }
}
