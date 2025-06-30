package com.example.authboard.domain.post.db;

import com.example.authboard.domain.post.controller.model.PostListRequest;
import com.example.authboard.domain.post.controller.model.PostResponse;
import com.example.authboard.domain.post.db.enums.PostStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepositoryCustom {
    Page<PostResponse> findAllPosts(PostListRequest request, Pageable pageable);
}
