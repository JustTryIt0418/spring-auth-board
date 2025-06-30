package com.example.authboard.domain.comment.controller;

import com.example.authboard.common.response.ApiResponse;
import com.example.authboard.domain.comment.business.CommentBusiness;
import com.example.authboard.domain.comment.controller.model.CommentRequest;
import com.example.authboard.domain.comment.controller.model.CommentResponse;
import com.example.authboard.domain.comment.controller.model.CommentUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
@Tag(name = "댓글", description = "댓글 관련 API")
public class CommentApiController {

    private final CommentBusiness commentBusiness;

    @Operation(summary = "댓글 등록", description = "게시글 ID, 댓글 내용을 입력받아 댓글을 등록합니다.")
    @PostMapping("")
    public ApiResponse<CommentResponse> createComment(
            @Valid
            @RequestBody CommentRequest request
    ) {
            return ApiResponse.ok(commentBusiness.createComment(request));
    }

    @Operation(summary = "댓글 수정", description = "댓글 ID, 댓글 내용을 입력받아 댓글을 수정합니다.")
    @PutMapping("")
    public ApiResponse<CommentResponse> updateComment(
            @Valid
            @RequestBody CommentUpdateRequest request
    ) {
        return ApiResponse.ok(commentBusiness.updateComment(request));
    }

    @Operation(summary = "댓글 삭제", description = "댓글 ID를 입력받아 댓글을 삭제합니다.")
    @DeleteMapping("/{commentId}")
    public ApiResponse<CommentResponse> deleteComment(
            @PathVariable("commentId") Long commentId
    ) {
        return ApiResponse.ok(commentBusiness.deleteComment(commentId));
    }
}
