package com.example.board.domain.post.controller;

import com.example.board.domain.member.entity.CustomUser;
import com.example.board.domain.member.exception.LoginRequiredException;
import com.example.board.domain.post.dto.response.PreferenceResponse;
import com.example.board.domain.post.service.PreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/preference")
public class PreferenceController {

    @Autowired
    private PreferenceService preferenceService;

    @PostMapping("/post")
    ResponseEntity<PreferenceResponse> togglePostPreference(
            @PathVariable Long postId,
            Authentication authentication
    ) {
        Long memberId = getMemberId(authentication);
        PreferenceResponse preferenceResponse = preferenceService.togglePostPreference(memberId, postId);
        return ResponseEntity.ok(preferenceResponse);
    }

    @PostMapping("/post-comment")
    ResponseEntity<PreferenceResponse> togglePostCommentPreference(
            @PathVariable Long postCommentId,
            Authentication authentication
    ) {
        Long memberId = getMemberId(authentication);
        PreferenceResponse preferenceResponse = preferenceService.togglePostCommentPreference(memberId, postCommentId);
        return ResponseEntity.ok(preferenceResponse);
    }

    private Long getMemberId(Authentication authentication) {
        if (authentication.getPrincipal() instanceof CustomUser) {
            return ((CustomUser) authentication.getPrincipal()).getUserId();
        }
        throw new LoginRequiredException();
    }
}
