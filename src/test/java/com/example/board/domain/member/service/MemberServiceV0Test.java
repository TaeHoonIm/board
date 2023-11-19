package com.example.board.domain.member.service;

import com.example.board.domain.member.dto.request.SignUpRequest;
import com.example.board.domain.member.entity.Gender;
import com.example.board.domain.member.entity.Member;
import com.example.board.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceV0Test {

    @Autowired
    private MemberServiceV0 memberServiceV0;
    @Autowired
    private MemberRepository memberRepository;
    @BeforeEach
    void setUp() {
    }

    @Test
    void signUp_Success() {

        SignUpRequest signUpRequest = new SignUpRequest(
                "test@test.com",
                "testPassword",
                "testName",
                "testNickname",
                "990315",
                Gender.MALE
        );

        memberServiceV0.signUp(signUpRequest);

        Member savedMember = memberRepository.findById(1L).get();

        assertEquals(signUpRequest.email(), savedMember.getEmail());
        assertEquals(signUpRequest.password(), savedMember.getPassword());
        assertEquals(signUpRequest.name(), savedMember.getName());
        assertEquals(signUpRequest.nickname(), savedMember.getNickname());
        assertEquals(signUpRequest.birth(), savedMember.getBirth());
        assertEquals(signUpRequest.gender(), savedMember.getGender());
    }

    @Test
    void signUp_Fail_DuplicatedEmail() {
        SignUpRequest signUpRequest = new SignUpRequest(
                "test@test.com",
                "testPassword",
                "testName",
                "testNickname",
                "990315",
                Gender.MALE
        );

        memberServiceV0.signUp(signUpRequest);

        SignUpRequest signUpRequest2 = new SignUpRequest(
                "test@test.com",
                "testPassword2",
                "testName2",
                "testNickname2",
                "990101",
                Gender.FEMALE
        );

        assertThrows(RuntimeException.class, () -> memberServiceV0.signUp(signUpRequest2));
    }

    @Test
    void signUp_Fail_DuplicatedNickname() {
        SignUpRequest signUpRequest = new SignUpRequest(
                "test@test.com",
                "testPassword",
                "testName",
                "testNickname",
                "990315",
                Gender.MALE
        );

        memberServiceV0.signUp(signUpRequest);

        SignUpRequest signUpRequest2 = new SignUpRequest(
                "test2@test.com",
                "testPassword2",
                "testName2",
                "testNickname",
                "990101",
                Gender.FEMALE
        );

        assertThrows(RuntimeException.class, () -> memberServiceV0.signUp(signUpRequest2));
    }
}