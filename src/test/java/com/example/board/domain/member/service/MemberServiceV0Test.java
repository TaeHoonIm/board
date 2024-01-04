package com.example.board.domain.member.service;

import com.example.board.domain.member.dto.request.LogInRequest;
import com.example.board.domain.member.dto.request.SignUpRequest;
import com.example.board.domain.member.dto.response.MemberResponse;
import com.example.board.domain.member.entity.Gender;
import com.example.board.domain.member.entity.Member;
import com.example.board.domain.member.exception.DuplicateEmailException;
import com.example.board.domain.member.exception.DuplicateNickNameException;
import com.example.board.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeAll;
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
        memberRepository.deleteAll();

        SignUpRequest signUpRequest = new SignUpRequest(
                "test@test.com",
                "testPassword",
                "testName",
                "testNickname",
                "990315",
                Gender.MALE
        );

        memberServiceV0.signUp(signUpRequest);
    }

    @Test
    void signUp_Success() {

        SignUpRequest signUpRequest = new SignUpRequest(
                "signUp@test.com",
                "testPassword",
                "signUp",
                "signUp",
                "990315",
                Gender.MALE
        );

        memberServiceV0.signUp(signUpRequest);

        Member savedMember = memberRepository.findByEmail(signUpRequest.email()).orElse(null);

        assertEquals(signUpRequest.email(), savedMember.getEmail());
        assertNotEquals(signUpRequest.password(), savedMember.getPassword());
        assertEquals(signUpRequest.name(), savedMember.getName());
        assertEquals(signUpRequest.nickname(), savedMember.getNickname());
        assertEquals(signUpRequest.birth(), savedMember.getBirth());
        assertEquals(signUpRequest.gender(), savedMember.getGender());
    }

    @Test
    void signUp_Fail_DuplicatedEmail() {
        SignUpRequest signUpRequest = new SignUpRequest(
                "test@test.com",
                "testPassword2",
                "testName2",
                "testNickname2",
                "990101",
                Gender.FEMALE
        );

        assertThrows(DuplicateEmailException.class, () -> memberServiceV0.signUp(signUpRequest));
    }

    @Test
    void signUp_Fail_DuplicatedNickname() {
        SignUpRequest signUpRequest = new SignUpRequest(
                "test2@test.com",
                "testPassword2",
                "testName2",
                "testNickname",
                "990101",
                Gender.FEMALE
        );

        assertThrows(DuplicateNickNameException.class, () -> memberServiceV0.signUp(signUpRequest));
    }

    @Test
    void login_Success() {
        LogInRequest logInRequest = new LogInRequest(
                "test@test.com",
                "testPassword"
        );

        memberServiceV0.login(logInRequest);

        Member member = memberRepository.findByEmail(logInRequest.email()).orElse(null);

        assertNotNull(member);
    }

    @Test
    void login_Fail_WrongEmail() {
        LogInRequest logInRequest = new LogInRequest(
                "wrong@test.com",
                "testPassword"
        );

        assertThrows(RuntimeException.class, () -> memberServiceV0.login(logInRequest));
    }

    @Test
    void login_Fail_WrongPassword() {
        LogInRequest logInRequest = new LogInRequest(
                "test@test.com",
                "wrongPassword"
        );

        assertThrows(RuntimeException.class, () -> memberServiceV0.login(logInRequest));
    }

    @Test
    void getMemberInfo_Success() {
        String email = "test@test.com";

        MemberResponse memberResponse = memberServiceV0.getMemberInfo(email);

        assertEquals(email, memberResponse.email());
        assertEquals("testName", memberResponse.name());
        assertEquals("testNickname", memberResponse.nickname());
        assertEquals("990315", memberResponse.birth());
    }

    @Test
    void deleteMember_Success() {
        String email = "test@test.com";

        memberServiceV0.deleteMember(email);

        assertFalse(memberRepository.existsByEmail(email));
    }
    
}