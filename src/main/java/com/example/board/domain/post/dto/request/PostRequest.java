package com.example.board.domain.post.dto.request;

import com.example.board.domain.member.entity.Member;
import com.example.board.domain.post.entity.Post;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PostRequest(

        @Size
        @NotNull(message = "카테고리를 선택해주세요.")
        String category,

        @Size
        @NotNull(message = "태그를 선택해주세요.")
        String tag,

        @Size(min = 1, max = 100, message = "제목은 1자 이상 100자 이하로 입력해주세요.")
        @NotNull(message = "제목을 입력해주세요.")
        String title,

        @Size(min = 1, message = "내용을 입력해주세요.")
        @NotNull(message = "내용을 입력해주세요.")
        String content
) {

    public Post toEntity(Member member) {
        return Post.builder()
                .member(member)
                .category(category).tag(tag)
                .title(title).content(content).build();
    }

}
