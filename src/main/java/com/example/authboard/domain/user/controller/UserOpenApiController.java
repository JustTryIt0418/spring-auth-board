package com.example.authboard.domain.user.controller;

import com.example.authboard.common.response.ApiResponse;
import com.example.authboard.domain.user.business.UserBusiness;
import com.example.authboard.domain.user.controller.model.LoginResponse;
import com.example.authboard.domain.user.controller.model.TokenRefreshRequest;
import com.example.authboard.domain.user.controller.model.TokenResponse;
import com.example.authboard.domain.user.controller.model.UserLoginRequest;
import com.example.authboard.domain.user.controller.model.UserRegisterRequest;
import com.example.authboard.domain.user.controller.model.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/open-api/users")
@RequiredArgsConstructor
@Tag(name = "사용자(인증 불필요)", description = "사용자 가입 및 로그인을 위해 인증이 없는 API")
public class UserOpenApiController {

    private final UserBusiness userBusiness;

    @Operation(summary = "회원가입", description = "사용자 정보를 입력받아 회원가입을 처리합니다.")
    @PostMapping("/register")
    public ApiResponse<UserResponse> register (
            @Valid
            @RequestBody UserRegisterRequest request
    ) {
        return ApiResponse.ok(userBusiness.register(request));
    }

    @Operation(summary = "로그인", description = "이메일과 비밀번호를 입력받아 사용자를 인증하고 JWT 토큰을 발급합니다.")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login (
            @Valid
            @RequestBody UserLoginRequest request
    ) {
        LoginResponse loginResponse = userBusiness.login(request);

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + loginResponse.getToken().getAccessToken())
                .body(ApiResponse.ok(loginResponse));
    }

    @Operation(summary = "토큰 재발급", description = "refresh token을 사용하여 새로운 access token을 발급합니다.")
    @PostMapping("/reissue")
    public ApiResponse<TokenResponse> reissue(
            @Valid
            @RequestBody TokenRefreshRequest request
    ) {
        return ApiResponse.ok(userBusiness.reissue(request));
    }
}
