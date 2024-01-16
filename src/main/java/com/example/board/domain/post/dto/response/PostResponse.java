package com.example.board.domain.post.dto.response;

import com.example.board.domain.post.entity.Post;

import java.time.LocalDateTime;

public record PostResponse(
        Long id,
        String category,
        String tag,
        String title,
        String content,
        String author,
        int viewCount,
        LocalDateTime createdDate,
        LocalDateTime updatedDate
) {
    public static PostResponse of(Post savedPost) {
        return new PostResponse(
                savedPost.getId(),
                savedPost.getCategory(),
                savedPost.getTag(),
                savedPost.getTitle(),
                savedPost.getContent(),
                savedPost.getMember().getName(),
                savedPost.getViewCount(),
                savedPost.getCreatedAt(),
                savedPost.getUpdatedAt()
        );
    }
}
