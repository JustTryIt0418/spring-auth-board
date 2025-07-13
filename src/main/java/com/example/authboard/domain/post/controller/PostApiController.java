package com.example.authboard.domain.post.controller;

import com.example.authboard.common.dto.PageDto;
import com.example.authboard.common.response.ApiResponse;
import com.example.authboard.domain.post.business.PostBusiness;
import com.example.authboard.domain.post.controller.model.PostListRequest;
import com.example.authboard.domain.post.controller.model.PostRequest;
import com.example.authboard.domain.post.controller.model.PostResponse;
import com.example.authboard.domain.post.controller.model.PostUpdateRequest;
import com.example.authboard.security.model.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Tag(name = "게시글", description = "게시글 관련 API")
public class PostApiController {

    private final PostBusiness postBusiness;

    @Operation(summary = "게시글 등록", description = "제목, 내용을 입력받아 게시글을 등록합니다.")
    @PostMapping("")
    public ApiResponse<PostResponse> createPost(
            @Valid
            @RequestBody PostRequest postRequest,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ApiResponse.ok(postBusiness.createPost(postRequest, userDetails));
    }

    @Operation(summary = "게시글 수정", description = "게시글 제목, 내용을 입력받아 게시글을 수정합니다.")
    @PutMapping("")
    public ApiResponse<PostResponse> updatePost (
            @Valid
            @RequestBody PostUpdateRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ApiResponse.ok(postBusiness.updatePost(request, userDetails));
    }

    @Operation(summary = "게시글 삭제", description = "게시글 ID를 입력받아 게시글을 삭제합니다.")
    @DeleteMapping("/{postId}")
    public ApiResponse<PostResponse> deletePost (
            @PathVariable("postId") Long postId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ApiResponse.ok(postBusiness.deletePost(postId, userDetails));
    }
}
