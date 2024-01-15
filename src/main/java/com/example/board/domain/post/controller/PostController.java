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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


    private Long getMemberId(Authentication authentication) {
        if (authentication.getPrincipal() instanceof CustomUser) {
            return ((CustomUser) authentication.getPrincipal()).getUserId();
        }
        throw new LoginRequiredException();
    }
}
