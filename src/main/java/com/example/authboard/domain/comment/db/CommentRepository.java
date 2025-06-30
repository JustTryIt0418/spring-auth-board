package com.example.authboard.domain.comment.db;

import com.example.authboard.domain.comment.db.enums.CommentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    List<CommentEntity> findAllByPostIdAndStatusOrderByIdAsc(Long postId, CommentStatus commentStatus);
}
