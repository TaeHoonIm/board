package com.example.board.domain.post.service;

import com.example.board.domain.post.dto.request.PostRequest;
import com.example.board.domain.post.dto.response.PostListResponse;
import com.example.board.domain.post.dto.response.PostResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PostServiceV1 implements PostService{
    @Override
    public PostResponse createPost(Long memberId, PostRequest postRequest) {
        return null;
    }

    @Override
    public PostResponse deletePost(Long memberId, Long postId) {
        return null;
    }

    @Override
    public PostResponse updatePost(Long memberId, Long postId, PostRequest postRequest) {
        return null;
    }

    @Override
    public PostResponse getPost(Long postId) {
        return null;
    }

    @Override
    public Page<PostListResponse> getAllPosts(int page, int size) {
        return null;
    }

    @Override
    public Page<PostListResponse> searchPosts(String type, String keyword, int page, int size) {
        return null;
    }
}
