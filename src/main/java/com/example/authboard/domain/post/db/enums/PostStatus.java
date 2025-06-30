package com.example.authboard.domain.post.db.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PostStatus {

    ACTIVE("게시중"),
    DELETED("삭제");

    private String description;
}
