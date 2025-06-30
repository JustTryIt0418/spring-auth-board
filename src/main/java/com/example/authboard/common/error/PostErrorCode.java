package com.example.authboard.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum PostErrorCode implements ErrorCodeIfs {

    POST_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), 3000, "게시글을 찾을 수 없습니다."),
    POST_NOT_AUTHORIZED(HttpStatus.FORBIDDEN.value(), 3001, "게시글에 대한 권한이 없습니다."),
    ;

    private final Integer httpStatusCode;

    private final Integer code;

    private final String description;
}
