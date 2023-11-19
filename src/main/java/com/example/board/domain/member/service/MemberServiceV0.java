package com.example.board.domain.member.service;

import com.example.board.domain.member.dto.request.SignUpRequest;
import com.example.board.domain.member.dto.response.SignUpResponse;
import com.example.board.domain.member.entity.Member;
import com.example.board.domain.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberServiceV0 implements MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public SignUpResponse signUp(SignUpRequest signUpRequest) {
        Member newMember = signUpRequest.toEntity();

        duplicateEmailCheck(newMember.getEmail());
        duplicateNicknameCheck(newMember.getNickname());

        newMember.addRole();
        memberRepository.save(newMember);

        return new SignUpResponse(newMember.getEmail(), newMember.getNickname());
    }

    @Override
    public void duplicateEmailCheck(String email) {
        if(memberRepository.existsByEmail(email)) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }
    }

    @Override
    public void duplicateNicknameCheck(String nickname) {
        if(memberRepository.existsByNickname(nickname)) {
            throw new RuntimeException("이미 존재하는 닉네임입니다.");
        }
    }

}
