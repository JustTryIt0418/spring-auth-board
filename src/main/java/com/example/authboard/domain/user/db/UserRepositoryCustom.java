package com.example.authboard.domain.user.db;

import com.example.authboard.domain.user.controller.model.UserListRequest;
import com.example.authboard.domain.user.controller.model.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositoryCustom {
    Page<UserResponse> findAllUsers(UserListRequest request, Pageable pageable);
}
