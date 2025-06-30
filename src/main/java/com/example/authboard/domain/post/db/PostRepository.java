package com.example.authboard.domain.post.db;

import com.example.authboard.domain.post.db.enums.PostStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface PostRepository extends JpaRepository<PostEntity, Long>, PostRepositoryCustom {
    Optional<PostEntity> findFirstByIdAndStatusOrderByIdDesc(Long postId, PostStatus postStatus);
}
