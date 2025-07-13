package com.example.authboard.domain.post.controller.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostUpdateRequest {

    @NotNull
    private Long postId;

    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
