package com.example.board.domain.member.controller;

import com.example.board.domain.member.dto.request.LogInRequest;
import com.example.board.domain.member.dto.request.SignUpRequest;
import com.example.board.domain.member.dto.response.MemberResponse;
import com.example.board.domain.member.dto.response.SignUpResponse;
import com.example.board.domain.member.service.MemberServiceV0;
import com.example.board.global.config.security.TokenInfo;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberServiceV0 memberService;

    @PostMapping("/signup")
    public SignUpResponse signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        log.info("signUpRequest: " + signUpRequest);
        return memberService.signUp(signUpRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenInfo> login(@Valid @RequestBody LogInRequest logInRequest) {
        TokenInfo tokenInfo = memberService.login(logInRequest);
        return ResponseEntity.ok().header("Authorization", tokenInfo.grantType(), tokenInfo.accessToken()).build();
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
