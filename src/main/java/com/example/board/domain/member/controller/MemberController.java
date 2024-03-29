package com.example.board.domain.member.controller;

import com.example.board.domain.member.dto.request.LogInRequest;
import com.example.board.domain.member.dto.request.SignUpRequest;
import com.example.board.domain.member.dto.response.MemberResponse;
import com.example.board.domain.member.dto.response.SignUpResponse;
import com.example.board.domain.member.service.MemberServiceV0;
import com.example.board.global.config.security.TokenInfo;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberServiceV0 memberService;

    @PostMapping("/email/verification")
    public ResponseEntity sendMessage(@RequestParam("email") @Valid String email) {
        memberService.sendMessage(email);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/email/verification")
    public boolean checkAuthCode(@RequestParam("email") @Valid String email,
                                 @RequestParam("authCode") @Valid String authCode) {
        return memberService.verifyAuthCode(email, authCode);
    }

    @PostMapping("/signup")
    public SignUpResponse signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        return memberService.signUp(signUpRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LogInRequest logInRequest) {
        TokenInfo tokenInfo = memberService.login(logInRequest);
        return ResponseEntity.ok()
                .header("Authorization", tokenInfo.grantType() + " " + tokenInfo.accessToken())
                .build();
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
