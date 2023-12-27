package com.example.board.global.config.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TokenProvider {

    private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);
    private static final String AUTHORITIES_KEY = "auth";
    private final String secret;
    private final long tokenValidityInMilliseconds;
    private Key secretKey;

    public TokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds) {
        this.secret = secret;
        this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000;
    }

    // 빈이 생성되고 의존성 주입까지 끝낸 후에 secret 값을 Base64 Decode해서 key 변수에 할당
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    // JWT 토큰을 생성하는 메소드
    public String createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInMilliseconds);

        // JWT 토큰 생성
        return Jwts.builder()
                .setSubject(authentication.getName()) // payload에 email을 넣음
                .claim(AUTHORITIES_KEY, authorities) // payload에 권한 정보를 넣음
                .signWith(secretKey, SignatureAlgorithm.HS512) // signature에 들어갈 secret값과 암호화 알고리즘을 넣음
                .setExpiration(validity) // 만료 시간 설정
                .compact();
    }

    // JWT 토큰을 파싱해서 Authentication 객체를 리턴하는 메소드
    public Authentication getAuthentication(String token) {
        // 토큰을 파싱해서 클레임을 추출
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        // 클레임에서 권한 정보를 빼냄
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        // 권한 정보를 이용해서 User 객체를 만들어서 리턴
        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    // JWT 토큰의 유효성을 검증하는 메소드
    public boolean validateToken(String token) {
        try {
            // 토큰을 파싱해서 발생하는 Exception들을 catch
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) { // 토큰이 만료되었을 때
            throw new ExpiredTokenException();
        } catch (UnsupportedOperationException e) { // 지원하지 않는 토큰일 때
            throw new InvalidTokenException();
        } catch (IllegalArgumentException e) { // 토큰이 비어있을 때
            throw new InvalidTokenException();
        } catch (MalformedJwtException e) { // 토큰이 손상되었을 때
            throw new InvalidTokenException();
        } catch (Exception e) {
            logger.error("토큰 검증 중 에러 발생: {}", e.getMessage());
            throw new InvalidTokenException();
        }
    }
}
