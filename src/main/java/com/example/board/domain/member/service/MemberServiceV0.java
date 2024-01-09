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
import com.example.board.global.config.security.TokenInfo;
import com.example.board.global.config.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class MemberServiceV0 implements MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public SignUpResponse signUp(SignUpRequest signUpRequest) {
        Member newMember = signUpRequest.toEntity();
        log.info("newMember: " + newMember);
        duplicateEmailCheck(newMember.getEmail());
        duplicateNicknameCheck(newMember.getNickname());
        newMember.encodePassword(passwordEncoder);
        newMember.addRole();
        memberRepository.save(newMember);
        log.info("newMember: " + newMember);

        return new SignUpResponse(newMember.getEmail(), newMember.getNickname());
    }

    @Override
    public TokenInfo login(LogInRequest logInRequest) {
        // 인증 토큰 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(logInRequest.email(), logInRequest.password());

        // authenticate 메소드가 실행될 때 CustomUserDetailsService의 loadUserByUsername 메소드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // 해당 인증 토큰을 SecurityContext에 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 인증 토큰을 기반으로 JWT 토큰 생성
        return jwtTokenProvider.createToken(authentication);
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
