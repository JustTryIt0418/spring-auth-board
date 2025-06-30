package com.example.authboard.common.response;

import com.example.authboard.common.error.ErrorCodeIfs;
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
public class ApiResponse<T> {

    private Boolean success;

    private T data;

    private Integer code;

    private String description;

    private LocalDateTime timestamp;

    public static <T> ApiResponse<T>  ok(T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static ApiResponse<Object> error(ErrorCodeIfs errorCodeIfs) {
        return ApiResponse.builder()
                .success(false)
                .code(errorCodeIfs.getCode())
                .description(errorCodeIfs.getDescription())
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static ApiResponse<Object> error(ErrorCodeIfs errorCodeIfs, String description) {
        return ApiResponse.builder()
                .success(false)
                .code(errorCodeIfs.getCode())
                .description(description)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
