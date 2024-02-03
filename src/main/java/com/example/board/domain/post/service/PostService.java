package com.example.board.domain.post.service;

import com.example.board.domain.post.dto.request.PostRequest;
import com.example.board.domain.post.dto.response.PostListResponse;
import com.example.board.domain.post.dto.response.PostResponse;
import org.springframework.data.domain.Page;


public interface PostService {

    PostResponse createPost(Long memberId, PostRequest postRequest);

    PostResponse deletePost(Long memberId, Long postId);

    PostResponse updatePost(Long memberId, Long postId, PostRequest postRequest);

    PostResponse getPost(Long postId);

    Page<PostListResponse> getAllPosts(int page, int size);

    Page<PostListResponse> searchPosts(String type, String keyword, int page, int size);
}
