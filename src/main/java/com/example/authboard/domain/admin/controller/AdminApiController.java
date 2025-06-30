package com.example.authboard.domain.admin.controller;

import com.example.authboard.common.dto.PageDto;
import com.example.authboard.common.response.ApiResponse;
import com.example.authboard.domain.admin.business.AdminBusiness;
import com.example.authboard.domain.admin.controller.model.UserRoleRequest;
import com.example.authboard.domain.comment.controller.model.CommentResponse;
import com.example.authboard.domain.comment.db.enums.CommentStatus;
import com.example.authboard.domain.post.controller.model.PostResponse;
import com.example.authboard.domain.post.db.enums.PostStatus;
import com.example.authboard.domain.user.controller.model.UserListRequest;
import com.example.authboard.domain.user.controller.model.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/api")
@RequiredArgsConstructor
@Tag(name = "관리자", description = "관리자용 API")
public class AdminApiController {

    private final AdminBusiness adminBusiness;

    @Operation(summary = "사용자 목록 조회", description = "검색타입, 검색어, 페이지 등을 받아 사용자 목록을 조회합니다.")
    @GetMapping("/users")
    public ApiResponse<PageDto<UserResponse>> getUserList(
            @ParameterObject UserListRequest request,
            @ParameterObject @PageableDefault(size = 10, page = 0, sort = "id",  direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ApiResponse.ok(adminBusiness.getUserList(request, pageable));
    }

    @Operation(summary = "사용자 조회", description = "사용자 ID를 받아 사용자를 조회합니다.")
    @GetMapping("/users/{userId}")
    public ApiResponse<UserResponse> getUser(
            @PathVariable(value = "userId") Long userId
    ) {
        return ApiResponse.ok(adminBusiness.getUser(userId));
    }

    @Operation(summary = "사용자 권한 변경", description = "사용자 ID, 권한을 받아 사용자의 권한을 변경합니다.")
    @PutMapping("/users/role")
    public ApiResponse<UserResponse> updateUserRole(
            @Valid
            @RequestBody UserRoleRequest request
    ) {
        return ApiResponse.ok(adminBusiness.updateUserRole(request));
    }

    @Operation(summary = "게시글 삭제", description = "게시글의 ID를 입력받아 게시글을 삭제합니다.")
    @DeleteMapping("/posts/{postId}")
    public ApiResponse<PostResponse> deletePost(
            @PathVariable(value = "postId") Long postId
    ) {
        return ApiResponse.ok(adminBusiness.updatePostStatus(postId, PostStatus.DELETED));
    }

    @Operation(summary = "댓글 삭제", description = "댓글의 ID를 입력받아 댓글을 삭제합니다.")
    @DeleteMapping("/comments/{commentId}")
    public ApiResponse<CommentResponse> deleteComment(
            @PathVariable(value = "commentId") Long commentId
    ) {
        return ApiResponse.ok(adminBusiness.updateCommentStatus(commentId, CommentStatus.DELETED));
    }

}
