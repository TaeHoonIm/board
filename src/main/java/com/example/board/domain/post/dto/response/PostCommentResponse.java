package com.example.board.domain.post.dto.response;

import com.example.board.domain.post.entity.PostComment;

import java.time.LocalDateTime;

public record PostCommentResponse(
        Long id,
        String content,
        String author,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static PostCommentResponse of(PostComment savedPostComment) {
        return new PostCommentResponse(
                savedPostComment.getId(),
                savedPostComment.getContent(),
                savedPostComment.getMember().getNickname(),
                savedPostComment.getCreatedAt(),
                savedPostComment.getUpdatedAt()
        );
    }
}
