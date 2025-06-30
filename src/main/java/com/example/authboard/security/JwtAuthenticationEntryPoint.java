package com.example.authboard.security;

import com.example.authboard.common.error.TokenErrorCode;
import com.example.authboard.common.exception.ApiException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String path = request.getRequestURI();
        log.info("path : {}", path);

        if (path.startsWith("/open-api") || path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs")) {
            return;
        }

        String accessToken = getAccessToken(request);

        if(accessToken == null){
            handlerExceptionResolver.resolveException(request, response, null, new ApiException(TokenErrorCode.TOKEN_NOT_FOUND));
        } else {
            handlerExceptionResolver.resolveException(request, response, null, authException);
        }
    }

    public String getAccessToken(HttpServletRequest request){
        String accessToken = request.getHeader("Authorization");
        if(StringUtils.hasText(accessToken) && accessToken.startsWith("Bearer ")){
            return accessToken.substring(7);
        }
        return null;
    }
}
