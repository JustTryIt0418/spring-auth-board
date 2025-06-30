package com.example.authboard.domain.admin.controller.model;

import com.example.authboard.domain.post.db.enums.PostStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostStatusRequest {

    private Long postId;

    private PostStatus postStatus;
}
