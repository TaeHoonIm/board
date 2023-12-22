package com.example.board.domain.member.dto.request;


public record LogInRequest (
        String email,
        String password
) {

}
