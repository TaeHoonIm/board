package com.example.board.global.config.security;

import com.example.board.global.error.exception.BusinessException;
import com.example.board.global.error.exception.ErrorCode;

public class InvalidTokenException extends BusinessException {

        public InvalidTokenException() {
            super(ErrorCode.INVALID_TOKEN);
        }
}
