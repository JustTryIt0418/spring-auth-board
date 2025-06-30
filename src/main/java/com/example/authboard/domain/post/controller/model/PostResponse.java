package com.example.authboard.domain.post.controller.model;

import com.example.authboard.domain.comment.controller.model.CommentResponse;
import com.example.authboard.domain.post.db.enums.PostStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponse {

    private Long id;

    private Long userId;

    private String title;

    private String content;

    private PostStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<CommentResponse> comments;
}
