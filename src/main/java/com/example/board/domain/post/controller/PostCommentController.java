package com.example.board.domain.post.controller;

import com.example.board.domain.member.entity.CustomUser;
import com.example.board.domain.member.exception.LoginRequiredException;
import com.example.board.domain.post.dto.request.PostCommentRequest;
import com.example.board.domain.post.dto.response.PostCommentResponse;
import com.example.board.domain.post.service.PostCommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post/comment")
public class PostCommentController {

    @Autowired
    private PostCommentServiceImpl postCommentService;

    @PostMapping
    public ResponseEntity<PostCommentResponse> createComment(
            @RequestBody PostCommentRequest postCommentRequest, Authentication authentication
    ) {
        Long memberId = getMemberId(authentication);
        PostCommentResponse postCommentResponse = postCommentService.createComment(memberId, postCommentRequest);
        return ResponseEntity.ok(postCommentResponse);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<PostCommentResponse> updateComment(
            @PathVariable Long commentId,
            @RequestBody PostCommentRequest postCommentRequest,
            Authentication authentication
    ) {
        Long memberId = getMemberId(authentication);
        PostCommentResponse postCommentResponse = postCommentService.updateComment(memberId, commentId, postCommentRequest);
        return ResponseEntity.ok(postCommentResponse);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<PostCommentResponse> deleteComment(
            @PathVariable Long commentId,
            Authentication authentication
    ) {
        Long memberId = getMemberId(authentication);
        PostCommentResponse postCommentResponse = postCommentService.deleteComment(memberId, commentId);
        return ResponseEntity.ok(postCommentResponse);
    }

    private Long getMemberId(Authentication authentication) {
        if (authentication.getPrincipal() instanceof CustomUser) {
            return ((CustomUser) authentication.getPrincipal()).getUserId();
        }
        throw new LoginRequiredException();
    }

}
