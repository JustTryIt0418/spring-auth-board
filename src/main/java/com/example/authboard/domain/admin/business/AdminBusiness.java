package com.example.authboard.domain.admin.business;

import com.example.authboard.annotation.Business;
import com.example.authboard.common.dto.PageDto;
import com.example.authboard.common.util.ObjectConverter;
import com.example.authboard.domain.admin.controller.model.UserRoleRequest;
import com.example.authboard.domain.comment.controller.model.CommentResponse;
import com.example.authboard.domain.comment.db.CommentEntity;
import com.example.authboard.domain.comment.db.enums.CommentStatus;
import com.example.authboard.domain.comment.service.CommentService;
import com.example.authboard.domain.post.controller.model.PostResponse;
import com.example.authboard.domain.post.db.PostEntity;
import com.example.authboard.domain.post.db.enums.PostStatus;
import com.example.authboard.domain.post.service.PostService;
import com.example.authboard.domain.user.controller.model.UserListRequest;
import com.example.authboard.domain.user.controller.model.UserResponse;
import com.example.authboard.domain.user.db.UserEntity;
import com.example.authboard.domain.user.db.enums.UserStatus;
import com.example.authboard.domain.user.service.UserService;
import com.example.authboard.security.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

@Business
@RequiredArgsConstructor
public class AdminBusiness {

    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;
    private final ObjectConverter converter;
    private final UserContext userContext;

    // 사용자 목록 조회
    public PageDto<UserResponse> getUserList(UserListRequest request, Pageable pageable) {
        return new PageDto<>(userService.getUserList(request, pageable));
    }

    // 사용자 상세 조회
    public UserResponse getUser(Long userId) {
        return converter.toObject(
                userService.findUserWithThrow(userId, UserStatus.REGISTERED),
                UserResponse.class
        );
    }

    // 사용자 권한 변경
    public UserResponse updateUserRole(UserRoleRequest request) {

        UserEntity userEntity = userService.findUserWithThrow(request.getUserId(), UserStatus.REGISTERED);

        userEntity.setRole(request.getUserRole());

        return converter.toObject(
                userService.saveUser(userEntity),
                UserResponse.class
        );
    }

    // 게시글 상태 변경
    public PostResponse updatePostStatus(Long postId, PostStatus status) {
        PostEntity postEntity = postService.getPostByIdWithThrow(postId);
        postEntity.setStatus(status);

        return converter.toObject(
                postService.savePost(postEntity),
                PostResponse.class
        );
    }

    // 댓글 상태 변경
    public CommentResponse updateCommentStatus(Long commentId, CommentStatus status) {
        CommentEntity commentEntity = commentService.getCommentWithThrow(commentId);
        commentEntity.setStatus(status);

        return converter.toObject(
                commentService.saveComment(commentEntity),
                CommentResponse.class
        );
    }
}
