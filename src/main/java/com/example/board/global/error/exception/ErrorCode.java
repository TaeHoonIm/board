package com.example.board.global.error.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400, "C001", "입력 값이 올바르지 않습니다."),
    METHOD_NOT_ALLOWED(405, "C002", "허용되지 않은 메소드입니다."),
    INTERNAL_SERVER_ERROR(500, "C003", "서버에 오류가 발생하였습니다."),
    INVALID_TYPE_VALUE(400, "C004", "입력 값의 타입이 올바르지 않습니다."),
    HANDLE_ACCESS_DENIED(403, "C005", "권한이 없습니다."),

    // Member
    DUPLICATE_EMAIL(400, "M001", "이미 존재하는 이메일입니다."),
    DUPLICATE_NICKNAME(400, "M002", "이미 존재하는 닉네임입니다."),
    NOT_EXIST_MEMBER(400, "M003", "존재하지 않는 회원입니다."),
    LOGIN_INPUT_INVALID(400, "M004", "이메일 또는 비밀번호가 올바르지 않습니다.");


    private final int status;
    private final String code;
    private final String message;
}
