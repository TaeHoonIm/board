package com.example.board.domain.member.service;

import com.example.board.domain.member.dto.request.MemberRegisterDto;
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

        MemberRegisterDto memberRegisterDto = new MemberRegisterDto(
                "test@test.com",
                "testPassword",
                "testName",
                "testNickname",
                "990315",
                Gender.MALE
        );

        memberServiceV0.signUp(memberRegisterDto);

        Member savedMember = memberRepository.findById(1L).get();

        assertEquals(memberRegisterDto.email(), savedMember.getEmail());
        assertEquals(memberRegisterDto.password(), savedMember.getPassword());
        assertEquals(memberRegisterDto.name(), savedMember.getName());
        assertEquals(memberRegisterDto.nickname(), savedMember.getNickname());
        assertEquals(memberRegisterDto.birth(), savedMember.getBirth());
        assertEquals(memberRegisterDto.gender(), savedMember.getGender());
    }
}