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

        duplicateEmailCheck(newMember.getEmail());
        duplicateNicknameCheck(newMember.getNickname());

        newMember.encodePassword(passwordEncoder);
        newMember.addRole();

        memberRepository.save(newMember);

        return new SignUpResponse(newMember.getEmail(), newMember.getNickname());
    }

    @Override
    public TokenInfo login(LogInRequest logInRequest) {
        // 인증 토큰 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                logInRequest.email(),
                logInRequest.password());
        // AuthenticationManager에게 인증을 요청하면, UserDetailsService가 호출됨
        // UserDetailsService는 loadUserByUsername 메서드를 통해 UserDetails 객체를 반환
        // AuthenticationManager는 loadUserByUsername 메서드가 반환한 UserDetails 객체의 비밀번호와 사용자가 입력한 비밀번호를 비교하여 인증 여부를 결정
        // 인증에 성공하면 Authentication 객체를 반환
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 인증 토큰을 기반으로 JWT 토큰 생성
        return jwtTokenProvider.createToken(authentication);
    }

    @Override
    public MemberResponse getMemberInfo(String email) {
        Member member = memberRepository.findByEmail(email).orElse(null);

        if (member == null) {
            throw new LogInInputInvalidException();
        }

        return new MemberResponse(member);
    }

    @Override
    public void deleteMember(String email) {
        Member member = memberRepository.findByEmail(email).orElse(null);

        if (member == null) {
            throw new LogInInputInvalidException();
        }

        memberRepository.delete(member);
    }

    @Override
    public void duplicateEmailCheck(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new DuplicateEmailException();
        }
    }

    @Override
    public void duplicateNicknameCheck(String nickname) {
        if (memberRepository.existsByNickname(nickname)) {
            throw new DuplicateNickNameException();
        }
    }

}
