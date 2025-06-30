package com.example.authboard.domain.post.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostListRequest {

    private String searchType;

    private String searchKeyword;
}
