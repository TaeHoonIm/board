package com.example.board.domain.member.dto.request;

import com.example.board.domain.member.entity.Gender;
import com.example.board.domain.member.entity.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record MemberRegisterDto (
        @Email(message = "이메일 형식이 아닙니다.")
        String email,
        @NotBlank(message = "비밀번호를 입력해주세요.")
        String password,
        @NotBlank(message = "이름을 입력해주세요.")
        String name,
        @NotBlank(message = "닉네임을 입력해주세요.")
        String nickname,
        @NotBlank(message = "생년월일을 입력해주세요.")
        String birth,
        @NotNull(message = "성별을 선택해주세요.")
        Gender gender
) {
        public Member toEntity() {
                return Member.builder().
                        email(email).password(password).name(name).nickname(nickname).birth(birth).gender(gender).build();
        }

}
