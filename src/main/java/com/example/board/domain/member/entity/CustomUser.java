package com.example.board.domain.member.entity;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class CustomUser extends User {
    Long userId;
    String email;
    String nickname;
    String password;
    Collection<GrantedAuthority> authorities;

    public CustomUser(Long userId, String email, String nickname, String password, Collection<GrantedAuthority> authorities) {
        super(email, password, authorities);
        this.userId = userId;
        this.nickname = nickname;
    }
}
