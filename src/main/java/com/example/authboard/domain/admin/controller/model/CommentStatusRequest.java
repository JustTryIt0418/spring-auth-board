package com.example.authboard.domain.admin.controller.model;

import com.example.authboard.domain.comment.db.enums.CommentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentStatusRequest {

    private Long commentId;

    private CommentStatus commentStatus;
}
