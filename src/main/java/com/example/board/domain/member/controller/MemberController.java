package com.example.board.domain.member.controller;

import com.example.board.domain.member.dto.request.LogInRequest;
import com.example.board.domain.member.dto.request.SignUpRequest;
import com.example.board.domain.member.dto.response.MemberResponse;
import com.example.board.domain.member.dto.response.SignUpResponse;
import com.example.board.domain.member.service.MemberServiceV0;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberServiceV0 memberService;

    @PostMapping("/signup")
    public SignUpResponse signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        return memberService.signUp(signUpRequest);
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody LogInRequest logInRequest) {
        memberService.login(logInRequest);
        return "로그인 성공";
    }

    @GetMapping
    public MemberResponse getMemberInfo(@Valid @RequestBody String email) {
        return memberService.getMemberInfo(email);
    }

    @DeleteMapping
    public String deleteMember(@Valid @RequestBody String email) {
        memberService.deleteMember(email);
        return "회원 탈퇴 성공";
    }

}
