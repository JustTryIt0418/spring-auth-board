package com.example.authboard.domain.comment.controller.model;

import com.example.authboard.domain.comment.db.enums.CommentStatus;
import com.example.authboard.domain.user.controller.model.UserResponse;
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
public class CommentResponse {

    private Long id;

    private SimplePostResponse post;

    private SimpleUserResponse user;

    private String content;

    private CommentStatus status;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class SimpleUserResponse {
        private Long id;
        private String email;
        private String nickname;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class SimplePostResponse {
        private Long id;
    }
}
