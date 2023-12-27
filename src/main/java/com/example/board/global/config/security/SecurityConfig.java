package com.example.board.global.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // token을 사용하는 방식이기 때문에 csrf를 disable합니다.
                .csrf().disable()

                // exception handling을 할 때 우리가 만든 클래스로 exception을 핸들링 하도록 합니다.
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                // h2-console을 위한 설정을 합니다.
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()

                // 세션을 사용하지 않기 때문에 STATELESS로 설정합니다.
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // 다음 리퀘스트에 대한 사용권한 체크를 하도록 합니다.
                .and()
                .authorizeRequests() // HttpServletRequest를 사용하는 요청들에 대한 접근제한 설정
                .requestMatchers("/member/signup").permitAll() // 회원가입은 누구나 접근 가능
                .requestMatchers("/member/login").permitAll() // 로그인은 누구나 접근 가능
                .requestMatchers(PathRequest.toH2Console()).permitAll() // h2-console, favicon.ico 요청 인증 무시
                .requestMatchers("/member/**").hasRole("USER") // member로 시작하는 리퀘스트는 USER 권한이 있어야 접근 가능
                .requestMatchers("/admin/**").hasRole("ADMIN") // admin으로 시작하는 리퀘스트는 ADMIN 권한이 있어야 접근 가능
                .anyRequest().authenticated() // 나머지 요청들은 모두 인증된 회원만 접근 가능

                // JwtFilter를 addFilterBefore로 등록했던 JwtSecurityConfig 클래스를 적용
                .and()
                .apply(new JwtSecurityConfig(tokenProvider)); // JwtSecurityConfig 클래스를 적용

        return httpSecurity.build();
    }
}
