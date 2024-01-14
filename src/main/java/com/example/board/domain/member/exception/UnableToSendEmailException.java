package com.example.board.domain.member.exception;

import com.example.board.global.error.exception.BusinessException;
import com.example.board.global.error.exception.ErrorCode;

public class UnableToSendEmailException extends BusinessException {
    public UnableToSendEmailException() {
        super(ErrorCode.UNABLE_TO_SEND_EMAIL);
    }
}
