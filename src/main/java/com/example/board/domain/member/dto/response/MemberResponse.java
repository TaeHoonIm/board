package com.example.board.domain.member.dto.response;

import com.example.board.domain.member.entity.Gender;
import com.example.board.domain.member.entity.Member;

public record MemberResponse(
        String email,
        String name,
        String nickname,
        String birth,
        Gender gender
) {
    public MemberResponse(Member member){
        this(member.getEmail(), member.getName(), member.getNickname(), member.getBirth(), member.getGender());
    }
}
