package com.example.board.domain.member.exception;

import com.example.board.global.error.exception.BusinessException;
import com.example.board.global.error.exception.ErrorCode;

public class UnableToSendAuthCodeException extends BusinessException {
    public UnableToSendAuthCodeException() {
        super(ErrorCode.UNABLE_TO_SEND_AUTH_CODE);
    }
}
