package com.example.authboard.domain.comment.business;

import com.example.authboard.annotation.Business;
import com.example.authboard.common.error.CommentErrorCode;
import com.example.authboard.common.exception.ApiException;
import com.example.authboard.common.util.ObjectConverter;
import com.example.authboard.domain.comment.controller.model.CommentRequest;
import com.example.authboard.domain.comment.controller.model.CommentResponse;
import com.example.authboard.domain.comment.controller.model.CommentUpdateRequest;
import com.example.authboard.domain.comment.db.CommentEntity;
import com.example.authboard.domain.comment.db.enums.CommentStatus;
import com.example.authboard.domain.comment.service.CommentService;
import com.example.authboard.domain.post.db.PostEntity;
import com.example.authboard.domain.post.service.PostService;
import com.example.authboard.domain.user.db.UserEntity;
import com.example.authboard.security.UserContext;
import com.example.authboard.security.model.CustomUserDetails;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@Business
@RequiredArgsConstructor
public class CommentBusiness {

    private final PostService postService;
    private final CommentService commentService;
    private final ObjectConverter converter;
    private final UserContext userContext;

    public CommentResponse createComment(CommentRequest request, CustomUserDetails userDetails) {

        PostEntity post = postService.getPostByIdWithThrow(request.getPostId());
        UserEntity user = converter.toObject(userDetails, UserEntity.class);

        CommentEntity commentEntity = converter.toObject(request, CommentEntity.class);
        commentEntity.setUser(user);
        commentEntity.setPost(post);
        commentEntity.setStatus(CommentStatus.ACTIVE);

        return converter.toObject(commentService.saveComment(commentEntity), CommentResponse.class);
    }

    public CommentResponse updateComment(CommentUpdateRequest request, CustomUserDetails userDetails) {
        CommentEntity comment = getAuthorizedComment(request.getCommentId(), userDetails);
        comment.setContent(request.getContent());

        return converter.toObject(commentService.saveComment(comment), CommentResponse.class);
    }

    public CommentResponse deleteComment(Long commentId, CustomUserDetails userDetails) {
        CommentEntity comment = getAuthorizedComment(commentId, userDetails);
        comment.setStatus(CommentStatus.DELETED);

        return converter.toObject(commentService.saveComment(comment), CommentResponse.class);
    }

    private CommentEntity getAuthorizedComment(Long commentId, CustomUserDetails userDetails) {
        CommentEntity comment = commentService.getCommentWithThrow(commentId);

        if (!Objects.equals(comment.getUser().getId(), userDetails.getId())) {
            throw new ApiException(CommentErrorCode.COMMENT_NOT_AUTHORIZED);
        }

        return comment;
    }
}
