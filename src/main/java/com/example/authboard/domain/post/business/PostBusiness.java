package com.example.authboard.domain.post.business;

import com.example.authboard.annotation.Business;
import com.example.authboard.common.dto.PageDto;
import com.example.authboard.common.error.PostErrorCode;
import com.example.authboard.common.exception.ApiException;
import com.example.authboard.common.util.ObjectConverter;
import com.example.authboard.domain.comment.controller.model.CommentResponse;
import com.example.authboard.domain.comment.service.CommentService;
import com.example.authboard.domain.post.controller.model.PostListRequest;
import com.example.authboard.domain.post.controller.model.PostRequest;
import com.example.authboard.domain.post.controller.model.PostResponse;
import com.example.authboard.domain.post.controller.model.PostUpdateRequest;
import com.example.authboard.domain.post.db.PostEntity;
import com.example.authboard.domain.post.db.enums.PostStatus;
import com.example.authboard.domain.post.service.PostService;
import com.example.authboard.domain.user.db.UserEntity;
import com.example.authboard.security.UserContext;
import com.example.authboard.security.model.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Objects;


@Business
@RequiredArgsConstructor
public class PostBusiness {

    private final PostService postService;
    private final CommentService commentService;
    private final ObjectConverter converter;
    private final UserContext userContext;

    public PostResponse createPost(PostRequest postRequest, CustomUserDetails userDetails) {
        UserEntity user = converter.toObject(userDetails, UserEntity.class);

        PostEntity post = converter.toObject(postRequest, PostEntity.class);
        post.setStatus(PostStatus.ACTIVE);
        post.setUser(user);

        return converter.toObject(postService.savePost(post), PostResponse.class);
    }

    public PageDto<PostResponse> getPostList(
            PostListRequest request,
            Pageable pageable
    ) {
        return new PageDto<>(postService.getPostList(request, pageable));
    }

    public PostResponse getPost(Long postId) {
        PostResponse post = converter.toObject(postService.getPostByIdWithThrow(postId), PostResponse.class);

        List<PostResponse.SimpleCommentResponse> comments =
                converter.toList(
                        commentService.getCommentListByPostId(postId),
                        PostResponse.SimpleCommentResponse.class
                );

        post.setComments(comments);

        return post;
    }

    public PostResponse updatePost(PostUpdateRequest postRequest, CustomUserDetails userDetails) {
        PostEntity post = getAuthorizedPost(postRequest.getPostId(), userDetails);
        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());

        return converter.toObject(postService.savePost(post), PostResponse.class);
    }

    public PostResponse deletePost(Long postId, CustomUserDetails userDetails) {
        PostEntity post = getAuthorizedPost(postId, userDetails);
        post.setStatus(PostStatus.DELETED);

        return converter.toObject(postService.savePost(post), PostResponse.class);
    }

    private PostEntity getAuthorizedPost(Long postId, CustomUserDetails userDetails) {
        PostEntity post = postService.getPostByIdWithThrow(postId);

        if (!Objects.equals(post.getUser().getId(), userDetails.getId())) {
            throw new ApiException(PostErrorCode.POST_NOT_AUTHORIZED);
        }

        return post;
    }
}
