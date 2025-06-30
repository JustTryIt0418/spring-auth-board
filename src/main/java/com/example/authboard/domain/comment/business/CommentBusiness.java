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
import com.example.authboard.domain.post.service.PostService;
import com.example.authboard.security.UserContext;
import lombok.RequiredArgsConstructor;

@Business
@RequiredArgsConstructor
public class CommentBusiness {

    private final PostService postService;
    private final CommentService commentService;
    private final ObjectConverter converter;
    private final UserContext userContext;

    public CommentResponse createComment(CommentRequest request) {

        postService.getPostByIdWithThrow(request.getPostId());

        Long userId = userContext.getCurrentUser().getUserEntity().getId();

        CommentEntity commentEntity = converter.toObject(request, CommentEntity.class);
        commentEntity.setUserId(userId);
        commentEntity.setStatus(CommentStatus.ACTIVE);

        return converter.toObject(commentService.saveComment(commentEntity), CommentResponse.class);
    }

    public CommentResponse updateComment(CommentUpdateRequest request) {
        Long userId = userContext.getCurrentUser().getUserEntity().getId();

        CommentEntity comment = commentService.getCommentWithThrow(request.getId());

        if (!comment.getUserId().equals(userId)) {
            throw new ApiException(CommentErrorCode.COMMENT_NOT_AUTHORIZED);
        }

        comment.setContent(request.getContent());

        return converter.toObject(commentService.saveComment(comment), CommentResponse.class);
    }

    public CommentResponse deleteComment(Long commentId) {
        Long userId = userContext.getCurrentUser().getUserEntity().getId();

        CommentEntity comment = commentService.getCommentWithThrow(commentId);

        if (!comment.getUserId().equals(userId)) {
            throw new ApiException(CommentErrorCode.COMMENT_NOT_AUTHORIZED);
        }

        comment.setStatus(CommentStatus.DELETED);

        return converter.toObject(commentService.saveComment(comment), CommentResponse.class);
    }
}
