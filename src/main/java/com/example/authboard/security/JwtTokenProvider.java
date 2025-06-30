package com.example.authboard.security;

import com.example.authboard.common.error.TokenErrorCode;
import com.example.authboard.common.error.UserErrorCode;
import com.example.authboard.common.exception.ApiException;
import com.example.authboard.domain.user.db.UserEntity;
import com.example.authboard.domain.user.db.UserRepository;
import com.example.authboard.domain.user.db.enums.UserStatus;
import com.example.authboard.domain.user.service.UserService;
import com.example.authboard.security.model.TokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.jackson.io.JacksonDeserializer;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;
    private final RedisTemplate<String, String> redisTemplate;

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        byte[] keyBytes = jwtProperties.getSecret().getBytes();
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenDto createToken(String email, List<String> role, long expireTime) {
        LocalDateTime expiredLocalDateTime = LocalDateTime.now().plusHours(expireTime);

        Date expiredAt = Date.from(
                expiredLocalDateTime.atZone(ZoneId.systemDefault())
                        .toInstant()
        );

        String token = Jwts.builder()
                .subject(email)
                .claim("role", role)
                .expiration(expiredAt)
                .signWith(secretKey)
                .compact();

        return TokenDto.builder()
                .token(token)
                .expiredAt(expiredLocalDateTime)
                .build();
    }

    public TokenDto createAccessToken(String email, List<String> role) {
        TokenDto accessToken = createToken(email, role, jwtProperties.getAccessTokenPlusHour());

        redisTemplate.opsForValue().set("accessToken:" + email, accessToken.getToken(), jwtProperties.getAccessTokenPlusHour(), TimeUnit.HOURS);

        return accessToken;
    }

    public TokenDto createRefreshToken(String email, List<String> role) {
        TokenDto refreshToken = createToken(email, role, jwtProperties.getRefreshTokenPlusHour());

        redisTemplate.opsForValue().set("refreshToken:" + email, refreshToken.getToken(), jwtProperties.getRefreshTokenPlusHour(), TimeUnit.HOURS);

        return refreshToken;
    }

    public String getEmailFromToken(String token) {
        return parseClaims(token).getPayload().getSubject();
    }

    public Collection<String> getRolesFromToken(String token) {
        Claims claims = parseClaims(token).getPayload();
        Object roles = claims.get("role");

        if (roles instanceof Collection<?>) {
            return ((Collection<?>) roles).stream()
                    .map(Object::toString)
                    .toList();
        }

        return Collections.emptyList(); // 또는 예외 처리
    }

    public Authentication getAuthentication(String token) {
        String email = getEmailFromToken(token);
        Collection<String> roles = getRolesFromToken(token);

        List<SimpleGrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();

        return new UsernamePasswordAuthenticationToken(
                email,
                null,
                authorities
        );
    }

    public Long getTokenRemainingTime(String token) {
        Claims claims = parseClaims(token).getPayload();
        Date expiration = claims.getExpiration(); // 만료 시각
        Date now = new Date(); // 현재 시각

        return expiration.getTime() - now.getTime();
    }

    public boolean validateToken(String token) {
        try {
            if ("black".equals(redisTemplate.opsForValue().get("blacklist:"+token))) {
                throw new ApiException(TokenErrorCode.INVALID_TOKEN);
            }

            Claims claims = parseClaims(token).getPayload();
            String email = claims.getSubject();

            boolean isAccess  = token.equals(redisTemplate.opsForValue().get("accessToken:" + email));
            boolean isRefresh = token.equals(redisTemplate.opsForValue().get("refreshToken:" + email));

            return isAccess || isRefresh;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Jws<Claims> parseClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .json(new JacksonDeserializer<>())
                    .build()
                    .parseSignedClaims(token);
        } catch (Exception e) {
            if (e instanceof SignatureException signatureException) {
                throw new ApiException(TokenErrorCode.INVALID_TOKEN, signatureException);
            } else if (e instanceof ExpiredJwtException expiredJwtException) {
                throw new ApiException(TokenErrorCode.EXPIRED_TOKEN, expiredJwtException);
            } else {
                throw new ApiException(TokenErrorCode.TOKEN_EXCEPTION, e);
            }
        }
    }
}
