package com.example.board.domain.member.service;

import com.example.board.domain.member.dto.request.LogInRequest;
import com.example.board.domain.member.dto.request.SignUpRequest;
import com.example.board.domain.member.dto.response.MemberResponse;
import com.example.board.domain.member.dto.response.SignUpResponse;

public interface MemberService {
    public SignUpResponse signUp(SignUpRequest signUpRequest);

    public void login(LogInRequest logInRequest);

    public MemberResponse getMemberInfo(String email);

    public void deleteMember(String email);

    public void duplicateEmailCheck(String email);
    public void duplicateNicknameCheck(String nickname);

}
