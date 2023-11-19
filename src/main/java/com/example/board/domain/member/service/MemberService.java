package com.example.board.domain.member.service;

import com.example.board.domain.member.dto.request.MemberRegisterDto;

public interface MemberService {
    public void signUp(MemberRegisterDto memberRegisterDto);
    public void duplicateEmailCheck(String email);
    public void duplicateNicknameCheck(String nickname);

}
