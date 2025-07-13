package com.example.authboard.domain.post.controller.model;

import com.example.authboard.domain.comment.controller.model.CommentResponse;
import com.example.authboard.domain.comment.db.enums.CommentStatus;
import com.example.authboard.domain.post.db.enums.PostStatus;
import com.example.authboard.domain.user.controller.model.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponse {

    private Long id;

    private SimpleUserResponse user;

    private String title;

    private String content;

    private PostStatus status;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    private List<SimpleCommentResponse> comments;

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
    public static class SimpleCommentResponse {
        private Long id;

        private SimpleUserResponse user;

        private String content;

        private OffsetDateTime createdAt;

        private OffsetDateTime updatedAt;
    }
}
