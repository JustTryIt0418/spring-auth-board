package com.example.authboard.domain.post.controller;

import com.example.authboard.common.dto.PageDto;
import com.example.authboard.common.response.ApiResponse;
import com.example.authboard.domain.post.business.PostBusiness;
import com.example.authboard.domain.post.controller.model.PostListRequest;
import com.example.authboard.domain.post.controller.model.PostResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/open-api/posts")
@RequiredArgsConstructor
@Tag(name = "게시글(인증불필요)", description = "게시글 관련 Open API")
public class PostOpenApiController {

    private final PostBusiness postBusiness;

    @Operation(summary = "게시글 조회", description = "게시글 ID를 입력받아 게시글을 조회합니다.")
    @GetMapping("/{postId}")
    public ApiResponse<PostResponse> getPost(
            @PathVariable("postId") Long postId
    ) {
        return ApiResponse.ok(postBusiness.getPost(postId));
    }

    @Operation(summary = "게시글 목록 조회", description = "게시글 검색타입, 검색어, 페이지 등을 받아 게시글 목록을 조회합니다.")
    @GetMapping("")
    public ApiResponse<PageDto<PostResponse>> getPostList(
            @ParameterObject PostListRequest request,
            @ParameterObject
            @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ApiResponse.ok(postBusiness.getPostList(request, pageable));
    }
}
