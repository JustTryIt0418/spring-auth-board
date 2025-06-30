package com.example.authboard.domain.comment.service;

import com.example.authboard.common.error.CommentErrorCode;
import com.example.authboard.common.exception.ApiException;
import com.example.authboard.domain.comment.db.CommentEntity;
import com.example.authboard.domain.comment.db.CommentRepository;
import com.example.authboard.domain.comment.db.enums.CommentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentEntity saveComment(CommentEntity commentEntity) {
        return commentRepository.save(commentEntity);
    }

    public List<CommentEntity> getCommentListByPostId(Long postId) {
        return commentRepository.findAllByPostIdAndStatusOrderByIdAsc(postId, CommentStatus.ACTIVE);
    }

    public CommentEntity getCommentWithThrow(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new ApiException(CommentErrorCode.COMMENT_NOT_FOUND, "댓글을 찾을 수 없습니다. id: " + id));
    }
}
