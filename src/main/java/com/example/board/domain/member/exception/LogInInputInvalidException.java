package com.example.board.domain.member.exception;

import com.example.board.global.error.exception.BusinessException;
import com.example.board.global.error.exception.ErrorCode;

public class LogInInputInvalidException extends BusinessException {
    public LogInInputInvalidException() {
        super(ErrorCode.LOGIN_INPUT_INVALID);
    }
}
