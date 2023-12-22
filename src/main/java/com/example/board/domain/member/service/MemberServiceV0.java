package com.example.board.domain.member.service;

import com.example.board.domain.member.dto.request.LogInRequest;
import com.example.board.domain.member.dto.request.SignUpRequest;
import com.example.board.domain.member.dto.response.MemberResponse;
import com.example.board.domain.member.dto.response.SignUpResponse;
import com.example.board.domain.member.entity.Member;
import com.example.board.domain.member.exception.DuplicateEmailException;
import com.example.board.domain.member.exception.DuplicateNickNameException;
import com.example.board.domain.member.exception.LogInInputInvalidException;
import com.example.board.domain.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
    public void login(LogInRequest logInRequest) {
        Optional<Member> member = memberRepository.findByEmail(logInRequest.email());
        if(member==null || !member.get().getPassword().equals(logInRequest.password())) {
            throw new LogInInputInvalidException();
        }
    }

    @Override
    public MemberResponse getMemberInfo(String email) {
        Member member = memberRepository.findByEmail(email).orElse(null);

        if(member == null) {
            throw new LogInInputInvalidException();
        }

        return new MemberResponse(member);
    }

    @Override
    public void deleteMember(String email) {
        Member member = memberRepository.findByEmail(email).orElse(null);

        if(member == null) {
            throw new LogInInputInvalidException();
        }

        memberRepository.delete(member);
    }

    @Override
    public void duplicateEmailCheck(String email) {
        if(memberRepository.existsByEmail(email)) {
            throw new DuplicateEmailException();
        }
    }

    @Override
    public void duplicateNicknameCheck(String nickname) {
        if(memberRepository.existsByNickname(nickname)) {
            throw new DuplicateNickNameException();
        }
    }

}
