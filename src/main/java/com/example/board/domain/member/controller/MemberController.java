package com.example.board.domain.member.controller;

import com.example.board.domain.member.dto.request.SignUpRequest;
import com.example.board.domain.member.dto.response.SignUpResponse;
import com.example.board.domain.member.service.MemberServiceV0;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberServiceV0 memberService;

    @PostMapping("/sign-up")
    public SignUpResponse signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        return memberService.signUp(signUpRequest);
    }

}
