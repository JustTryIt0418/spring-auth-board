package com.example.authboard.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CommentErrorCode implements ErrorCodeIfs {

    COMMENT_NOT_FOUND(HttpStatus.BAD_REQUEST.value(), 4000, "댓글을 찾을 수 없습니다."),
    COMMENT_NOT_AUTHORIZED(HttpStatus.UNAUTHORIZED.value(), 4001, "권한이 없습니다."),
    ;

    private final Integer httpStatusCode;

    private final Integer code;

    private final String description;
}
