package com.example.board.domain.post.dto.response;

import com.example.board.domain.post.entity.Post;

import java.time.LocalDateTime;

public record PostResponse(
        Long id,
        String title,
        String content,
        String author,
        LocalDateTime createdDate
) {
    public static PostResponse of(Post savedPost) {
        return new PostResponse(
                savedPost.getId(),
                savedPost.getTitle(),
                savedPost.getContent(),
                savedPost.getMember().getName(),
                savedPost.getCreatedAt()
        );
    }
}
