package com.example.authboard.domain.post.service;

import com.example.authboard.common.error.PostErrorCode;
import com.example.authboard.common.exception.ApiException;
import com.example.authboard.domain.post.controller.model.PostListRequest;
import com.example.authboard.domain.post.controller.model.PostResponse;
import com.example.authboard.domain.post.db.PostEntity;
import com.example.authboard.domain.post.db.PostRepository;
import com.example.authboard.domain.post.db.enums.PostStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    // 게시글 저장
    public PostEntity savePost(PostEntity postEntity) {
        return postRepository.save(postEntity);
    }

    // 게시글 조회
    public PostEntity getPostByIdWithThrow(Long postId) {
        return postRepository.findFirstByIdAndStatusOrderByIdDesc(postId, PostStatus.ACTIVE)
                .orElseThrow(() -> new ApiException(PostErrorCode.POST_NOT_FOUND, "게시글을 찾을 수 없습니다. postId: " + postId));
    }

    // 게시글 목록 조회
    public Page<PostResponse> getPostList(PostListRequest request, Pageable pageable) {
        return postRepository.findAllPosts(request, pageable);
    }
}
