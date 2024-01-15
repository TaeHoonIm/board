package com.example.board.domain.member.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUser extends User {
    Long userId;
    String nickname;

    public CustomUser(Long userId, String email, String nickname, String password, Collection<GrantedAuthority> authorities) {
        super(email, password, authorities);
        this.userId = userId;
        this.nickname = nickname;
    }

    public Long getUserId() {
        return userId;
    }

    public String getNickname() {
        return nickname;
    }
}
