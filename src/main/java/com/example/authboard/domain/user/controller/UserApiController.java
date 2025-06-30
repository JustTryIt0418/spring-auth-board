package com.example.authboard.domain.user.controller;

import com.example.authboard.common.response.ApiResponse;
import com.example.authboard.domain.user.business.UserBusiness;
import com.example.authboard.domain.user.controller.model.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "사용자", description = "사용자 정보, 로그아웃을 위해 인증이 필요한 API")
public class UserApiController {

    private final UserBusiness userBusiness;

    @Operation(summary = "내 정보", description = "로그인 한 사용자 정보를 조회합니다.")
    @GetMapping("/me")
    public ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.ok(userBusiness.getMyInfo());
    }

    @Operation(summary = "로그아웃", description = "현재 계정을 로그아웃 처리합니다.")
    @PostMapping("/logout")
    public ApiResponse<Void> logout() {
        return ApiResponse.ok(userBusiness.logout());
    }
}
