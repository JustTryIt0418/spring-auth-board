package com.example.authboard.domain.user.business;

import com.example.authboard.annotation.Business;
import com.example.authboard.common.error.TokenErrorCode;
import com.example.authboard.common.exception.ApiException;
import com.example.authboard.common.util.ObjectConverter;
import com.example.authboard.domain.user.controller.model.LoginResponse;
import com.example.authboard.domain.user.controller.model.TokenRefreshRequest;
import com.example.authboard.domain.user.controller.model.UserLoginRequest;
import com.example.authboard.domain.user.controller.model.UserRegisterRequest;
import com.example.authboard.domain.user.controller.model.UserResponse;
import com.example.authboard.domain.user.db.UserEntity;
import com.example.authboard.domain.user.db.enums.UserRole;
import com.example.authboard.domain.user.db.enums.UserStatus;
import com.example.authboard.domain.user.service.UserService;
import com.example.authboard.security.JwtTokenProvider;
import com.example.authboard.domain.user.controller.model.TokenResponse;
import com.example.authboard.security.UserContext;
import com.example.authboard.security.model.TokenDto;
import com.example.authboard.security.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Business
@RequiredArgsConstructor
public class UserBusiness {

    private final UserContext userContext;
    private final UserService userService;
    private final ObjectConverter converter;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;
    private final PasswordEncoder passwordEncoder;

    public UserResponse register(UserRegisterRequest request) {

        UserEntity userEntity = converter.toObject(request, UserEntity.class);
        userEntity.setRole(UserRole.ROLE_USER);
        userEntity.setStatus(UserStatus.REGISTERED);
        userEntity.setPassword(passwordEncoder.encode(request.getPassword()));

        return converter.toObject(userService.saveUser(userEntity), UserResponse.class);
    }

    public LoginResponse login(UserLoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        String email = authentication.getName();

        TokenResponse token = generateTokens(email, roles);
        User user = (User) authentication.getPrincipal();
        UserResponse userResponse = converter.toObject(user.getUserEntity(), UserResponse.class);

        return LoginResponse.builder()
                .user(userResponse)
                .token(token)
                .build();
    }

    public UserResponse getMyInfo() {
        return converter.toObject(userContext.getCurrentUser().getUserEntity(), UserResponse.class);
    }

    public TokenResponse reissue(TokenRefreshRequest request) {
        String refreshToken = request.getRefreshToken();

        jwtTokenProvider.validateToken(refreshToken);
        String email = jwtTokenProvider.getEmailFromToken(refreshToken);
        List<String> roles = (List<String>) jwtTokenProvider.getRolesFromToken(refreshToken);

        String savedRefreshToken = redisTemplate.opsForValue().get("refreshToken:" + email);
        if (!refreshToken.equals(savedRefreshToken)) {
            throw new ApiException(TokenErrorCode.INVALID_TOKEN);
        }

        return generateTokens(email, roles);
    }

    public Void logout() {
        User user = userContext.getCurrentUser();
        String email = user.getUserEntity().getEmail();

        String accessToken = redisTemplate.opsForValue().get("accessToken:" + email);
        Long remainingTime = jwtTokenProvider.getTokenRemainingTime(accessToken);

        redisTemplate.opsForValue().set("blacklist:" + accessToken, "black", remainingTime, TimeUnit.MILLISECONDS);
        redisTemplate.delete("refreshToken:" + email);

        return null;
    }

    private TokenResponse generateTokens(String email, List<String> roles) {
        TokenDto access = jwtTokenProvider.createAccessToken(email, roles);
        TokenDto refresh = jwtTokenProvider.createRefreshToken(email, roles);

        return TokenResponse.builder()
                .accessToken(access.getToken())
                .accessTokenExpiredAt(access.getExpiredAt())
                .refreshToken(refresh.getToken())
                .refreshTokenExpiredAt(refresh.getExpiredAt())
                .build();
    }
}
