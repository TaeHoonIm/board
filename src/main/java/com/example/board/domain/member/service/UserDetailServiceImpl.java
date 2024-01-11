package com.example.board.domain.member.service;

import com.example.board.domain.member.entity.CustomUser;
import com.example.board.domain.member.entity.Member;
import com.example.board.domain.member.exception.LogInInputInvalidException;
import com.example.board.domain.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new LogInInputInvalidException());
        return createUserDetails(member);
    }

    private UserDetails createUserDetails(Member member) {
        return new CustomUser(
                member.getId(),
                member.getEmail(),
                member.getNickname(),
                member.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + member.getRole().toString()))
        );
    }
}
