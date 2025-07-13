package com.example.authboard.domain.user.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenResponse {

    private String accessToken;

    private OffsetDateTime accessTokenExpiredAt;

    private String refreshToken;

    private OffsetDateTime refreshTokenExpiredAt;
}
