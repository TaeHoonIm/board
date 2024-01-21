package com.example.board.domain.member.exception;

import com.example.board.global.error.exception.BusinessException;
import com.example.board.global.error.exception.ErrorCode;

public class LoginRequiredException extends BusinessException {
    public LoginRequiredException() {
        super(ErrorCode.LOGIN_REQUIRED);
    }
}
