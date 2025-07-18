package com.example.authboard.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum TokenErrorCode implements ErrorCodeIfs {

    INVALID_TOKEN(HttpStatus.BAD_REQUEST.value(), 2000, "유효하지 않은 토큰"),
    EXPIRED_TOKEN(HttpStatus.BAD_REQUEST.value(), 2001, "만료된 토큰"),
    TOKEN_EXCEPTION(HttpStatus.BAD_REQUEST.value(), 2002, "토큰 알수없는 에러"),
    TOKEN_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), 2003, "인증 헤더 토큰 없음")
    ;

    private final Integer httpStatusCode;

    private final Integer code;

    private final String description;
}
