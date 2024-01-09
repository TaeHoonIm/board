package com.example.board.global.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;


@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    private final JwtTokenProvider jwtTokenProvider;

    // 실제 필터링 로직
    // JwtFilter는 토큰의 인증정보를 SecurityContext에 저장하는 역할을 수행
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String jwt = resolveToken(httpServletRequest);

        // resolveToken 메소드를 통해 토큰을 받아옴.
        // 토큰의 유효성 검증을 수행
        if (jwt != null && jwtTokenProvider.validateToken(jwt)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(jwt);
            // 토큰이 정상적으로 검증되었다면 Authentication 객체를 받아온다.
            // SecurityContext에 Authentication 객체를 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }

    // Request Header에서 토큰 정보를 꺼내오기 위한 resolveToken 메소드
    // 토큰 정보가 저장될 때는 "Bearer"라는 문자열이 앞에 붙어서 저장되기 때문에 이를 제거해주는 역할을 수행
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
