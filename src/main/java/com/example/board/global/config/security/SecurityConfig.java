package com.example.board.global.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // h2-console을 위한 설정
    @Bean
    @ConditionalOnProperty(name = "spring.h2.console.enabled", havingValue = "true")
    public WebSecurityCustomizer configureH2ConsoleEnable() {
        return web -> web.ignoring()
                .requestMatchers(PathRequest.toH2Console());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, HandlerMappingIntrospector introspector) throws Exception {

        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);

        httpSecurity
                .csrf().disable() // token을 사용하는 방식이기 때문에 csrf를 disable
                .httpBasic().disable() // rest api이므로 기본설정 사용안함. 기본설정은 비인증시 로그인폼 화면으로 redirect

                // h2-console을 위한 설정
                .headers()
                .frameOptions()
                .sameOrigin()

                // 세션을 사용하지 않기 때문에 STATELESS로 설정
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // 다음 리퀘스트에 대한 사용권한 체크를 하도록 지정
                .and()
                .authorizeHttpRequests(request -> request // HttpServletRequest를 사용하는 요청들에 대한 접근제한 설정
                        .requestMatchers(mvcMatcherBuilder.pattern("/member/signup")).permitAll() // 회원가입은 누구나 접근 가능
                        .requestMatchers(mvcMatcherBuilder.pattern("/member/login")).permitAll() // 로그인은 누구나 접근 가능
                        .requestMatchers(mvcMatcherBuilder.pattern("/member/email/**")).permitAll() // email 관련 요청은 누구나 접근 가능
                        .requestMatchers(PathRequest.toH2Console()).permitAll() // h2-console, favicon.ico 요청 인증 무시
                        .requestMatchers(mvcMatcherBuilder.pattern("/member/**")).hasRole("USER") // member로 시작하는 리퀘스트는 USER 권한이 있어야 접근 가능
                        .requestMatchers(mvcMatcherBuilder.pattern("/admin/**")).hasRole("ADMIN") // admin으로 시작하는 리퀘스트는 ADMIN 권한이 있어야 접근 가능
                        .anyRequest().authenticated() // 나머지 요청들은 모두 인증된 회원만 접근 가능
                )
                .addFilter(corsFilter()) // cors 필터 추가
                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class
                ) // JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 전에 넣는다.
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint); // 유효한 자격증명을 제공하지 않고 접근하려 할 때 401 Unauthorized 에러를 리턴할 클래스

        return httpSecurity.build();
    }

    // react와의 통신을 위한 cors 설정
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addExposedHeader("Authorization");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
