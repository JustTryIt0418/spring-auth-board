package com.example.authboard.domain.comment.db.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CommentStatus {

    ACTIVE("게시중"),
    DELETED("삭제");

    private String description;
}
