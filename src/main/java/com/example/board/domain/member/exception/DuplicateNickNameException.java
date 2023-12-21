package com.example.board.domain.member.exception;

import com.example.board.global.error.exception.BusinessException;
import com.example.board.global.error.exception.ErrorCode;

public class DuplicateNickNameException extends BusinessException {
    public DuplicateNickNameException() {
        super(ErrorCode.DUPLICATE_NICKNAME);
    }
}
