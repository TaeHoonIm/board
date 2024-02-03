package com.example.board.domain.post.dto.response;

import com.example.board.domain.post.entity.Post;

import java.time.LocalDateTime;

public record PostListResponse(
        Long id,
        String category,
        String tag,
        String title,
        String author,
        int viewCount,
        int commentCount,
        int preferenceCount,
        LocalDateTime createdDate,
        LocalDateTime updatedDate
) {
    public static PostListResponse of(Post post) {
        return new PostListResponse(
                post.getId(),
                post.getCategory(),
                post.getTag(),
                post.getTitle(),
                post.getMember().getNickname(),
                post.getViewCount(),
                post.getCommentList() == null ? 0 : post.getCommentList().size(),
                post.getPostPreferenceList() == null ? 0 : post.getPostPreferenceList().size(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }
}
