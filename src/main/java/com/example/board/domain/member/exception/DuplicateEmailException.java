package com.example.board.domain.member.exception;

import com.example.board.global.error.exception.BusinessException;
import com.example.board.global.error.exception.ErrorCode;

public class DuplicateEmailException extends BusinessException {
        public DuplicateEmailException() {
            super(ErrorCode.DUPLICATE_EMAIL);
        }
}
