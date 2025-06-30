package com.example.authboard.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements ErrorCodeIfs {

    LOGIN_INFO_NOT_MATCH(HttpStatus.BAD_REQUEST.value(), 1400, "아이디 또는 비밀번호가 일치하지 않습니다."),
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), 1404, "사용자를 찾을 수 없습니다."),
    ;

    private final Integer httpStatusCode;

    private final Integer code;

    private final String description;
}
