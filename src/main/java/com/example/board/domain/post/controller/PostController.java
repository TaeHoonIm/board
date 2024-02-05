package com.example.board.domain.post.controller;

import com.example.board.domain.member.entity.CustomUser;
import com.example.board.domain.member.exception.LoginRequiredException;
import com.example.board.domain.post.dto.request.PostRequest;
import com.example.board.domain.post.dto.response.PostListResponse;
import com.example.board.domain.post.dto.response.PostResponse;
import com.example.board.domain.post.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody PostRequest postRequest, Authentication authentication) {
        Long memberId = getMemberId(authentication);
        PostResponse postResponse = postService.createPost(memberId, postRequest);
        return new ResponseEntity<>(postResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<PostResponse> deletePost(@PathVariable Long postId, Authentication authentication) {
        Long memberId = getMemberId(authentication);
        PostResponse postResponse = postService.deletePost(memberId, postId);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable Long postId, @RequestBody PostRequest postRequest, Authentication authentication) {
        Long memberId = getMemberId(authentication);
        PostResponse postResponse = postService.updatePost(memberId, postId, postRequest);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long postId) {
        PostResponse postResponse = postService.getPost(postId);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<Page<PostListResponse>> getAllPosts(@RequestParam int page, @RequestParam int size) {
        Page<PostListResponse> postListResponses = postService.getAllPosts(page, size);
        return new ResponseEntity<>(postListResponses, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<PostListResponse>> searchPosts(
            @RequestParam String type, @RequestParam String keyword,
            @RequestParam int page, @RequestParam int size) {
        Page<PostListResponse> postListResponses = postService.searchPosts(type, keyword, page, size);
        return new ResponseEntity<>(postListResponses, HttpStatus.OK);
    }

    private Long getMemberId(Authentication authentication) {
        if (authentication.getPrincipal() instanceof CustomUser) {
            return ((CustomUser) authentication.getPrincipal()).getUserId();
        }
        throw new LoginRequiredException();
    }
}
