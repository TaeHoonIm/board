package com.example.board.domain.member.service;

import com.example.board.domain.member.dto.request.MemberRegisterDto;
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
    public void signUp(MemberRegisterDto memberRegisterDto) {
        Member newMember = memberRegisterDto.toEntity();
        newMember.addRole();
        memberRepository.save(newMember);
    }

    @Override
    public void duplicateEmailCheck(String email) {

    }

    @Override
    public void duplicateNicknameCheck(String nickname) {

    }
}
