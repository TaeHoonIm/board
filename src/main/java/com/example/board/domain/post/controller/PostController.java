package com.example.board.domain.post.controller;

import com.example.board.domain.member.entity.CustomUser;
import com.example.board.domain.member.exception.LoginRequiredException;
import com.example.board.domain.post.dto.request.PostRequest;
import com.example.board.domain.post.dto.response.PostResponse;
import com.example.board.domain.post.service.PostServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostServiceImpl postService;

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


    private Long getMemberId(Authentication authentication) {
        if (authentication.getPrincipal() instanceof CustomUser) {
            return ((CustomUser) authentication.getPrincipal()).getUserId();
        }
        throw new LoginRequiredException();
    }
}
