package com.example.board.domain.post.service;

import com.example.board.domain.post.dto.response.PreferenceResponse;

public interface PreferenceService {

    PreferenceResponse togglePostPreference(Long memberId, Long postId);

    PreferenceResponse togglePostCommentPreference(Long memberId, Long postCommentId);

    boolean isPostPreference(Long memberId, Long postId);

    boolean isPostCommentPreference(Long memberId, Long postCommentId);
}
