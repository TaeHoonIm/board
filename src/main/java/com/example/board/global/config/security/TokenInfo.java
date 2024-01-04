package com.example.board.global.config.security;

public record TokenInfo(
        String grantType,
        String accessToken
) {
}
