package com.example.board.domain.member.exception;

import com.example.board.global.error.exception.BusinessException;
import com.example.board.global.error.exception.ErrorCode;

public class NotExistMemberException extends BusinessException {
    public NotExistMemberException() {
        super(ErrorCode.NOT_EXIST_MEMBER);
    }
}
