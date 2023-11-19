package com.example.board.domain.member.service;

import com.example.board.domain.member.dto.request.SignUpRequest;
import com.example.board.domain.member.dto.response.SignUpResponse;

public interface MemberService {
    public SignUpResponse signUp(SignUpRequest signUpRequest);
    public void duplicateEmailCheck(String email);
    public void duplicateNicknameCheck(String nickname);

}
