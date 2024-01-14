package com.example.board.domain.member.service;

import com.example.board.domain.member.dto.request.LogInRequest;
import com.example.board.domain.member.dto.request.SignUpRequest;
import com.example.board.domain.member.dto.response.MemberResponse;
import com.example.board.domain.member.dto.response.SignUpResponse;
import com.example.board.domain.member.entity.Member;
import com.example.board.domain.member.exception.DuplicateEmailException;
import com.example.board.domain.member.exception.DuplicateNickNameException;
import com.example.board.domain.member.exception.LogInInputInvalidException;
import com.example.board.domain.member.exception.UnableToSendAuthCodeException;
import com.example.board.domain.member.repository.MemberRepository;
import com.example.board.global.config.security.TokenInfo;
import com.example.board.global.config.security.JwtTokenProvider;
import com.example.board.global.service.RedisServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.Random;

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

    @Autowired
    private EmailVerificationServiceImpl emailService;

    @Autowired
    private RedisServiceImpl redisService;

    private long authCodeExpirationTime = 3 * 60 * 1000; // 인증 코드 만료 시간 (3분)

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

    //TODO: 비동기 & 멀티 쓰레드를 통해 이메일 인증 코드를 전송하여 속도를 개선하는 방법도 고려해볼 것
    @Override
    public void sendMessage(String toEmail) {
        duplicateEmailCheck(toEmail);

        String title = "이메일 인증 번호";
        String authCode = createAuthCode();

        emailService.SendEmail(toEmail, title, authCode);
        // 인증 코드를 Redis에 저장
        redisService.setValues("AuthCode " + toEmail,
                authCode, Duration.ofMillis(authCodeExpirationTime));
    }

    private String createAuthCode() {
        int authCodeLength = 6;
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < authCodeLength; i++) {
                stringBuilder.append(random.nextInt(10));
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            log.debug("MemberService.createAuthCode() exception occur");
            throw new UnableToSendAuthCodeException();
        }
    }

    public boolean verifyAuthCode(String email, String authCode) {
        duplicateEmailCheck(email);

        String redisAuthCode = (String) redisService.getValues("AuthCode " + email);
        boolean isAuthCodeValid = redisService.checkExistsValue(redisAuthCode) && redisAuthCode.equals(redisAuthCode);

        return isAuthCodeValid;
    }

}
