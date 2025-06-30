package com.example.authboard.exceptionhandler;

import com.example.authboard.common.error.ErrorCode;
import com.example.authboard.common.error.UserErrorCode;
import com.example.authboard.common.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @Order(2)
    @ExceptionHandler(value = InsufficientAuthenticationException.class)
    public ResponseEntity<ApiResponse<Object>> exception(InsufficientAuthenticationException exception) {
        log.error("", exception);

        return ResponseEntity
                .status(401)
                .body(
                        ApiResponse.error(ErrorCode.UNAUTHORIZED)
                );
    }

    @Order
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiResponse<Object>> exception(Exception exception) {
        log.error("", exception);

        return ResponseEntity
                .status(500)
                .body(
                        ApiResponse.error(ErrorCode.SERVER_ERROR)
                );
    }
}
