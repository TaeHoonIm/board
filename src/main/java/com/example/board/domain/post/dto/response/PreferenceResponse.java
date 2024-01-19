package com.example.board.domain.post.dto.response;

import com.example.board.domain.member.entity.Member;
import com.example.board.domain.post.entity.Post;
import com.example.board.domain.post.entity.PostComment;

public record PreferenceResponse(
        Long targetId,
        String targetNickname
) {
    public static PreferenceResponse of(Post post, Member member) {
        return new PreferenceResponse(
                post.getId(),
                member.getNickname()
        );
    }

    public static PreferenceResponse of(PostComment postComment, Member member) {
        return new PreferenceResponse(
                postComment.getId(),
                member.getNickname()
        );
    }
}
