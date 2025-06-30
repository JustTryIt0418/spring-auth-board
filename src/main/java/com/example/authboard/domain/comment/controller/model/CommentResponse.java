package com.example.authboard.domain.comment.controller.model;

import com.example.authboard.domain.comment.db.enums.CommentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponse {

    private Long id;

    private Long postId;

    private Long userId;

    private String content;

    private CommentStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
