package com.example.board.domain.post.dto.request;

import com.example.board.domain.member.entity.Member;
import com.example.board.domain.post.entity.Post;
import com.example.board.domain.post.entity.PostComment;

public record PostCommentRequest(
        Long postId,
        String content
) {
    public PostComment toEntity(Post savedPost, Member member) {
        return PostComment.builder()
                .post(savedPost)
                .content(content)
                .member(member)
                .build();
    }
}
