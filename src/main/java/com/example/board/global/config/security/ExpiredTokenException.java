package com.example.board.global.config.security;

import com.example.board.global.error.exception.BusinessException;
import com.example.board.global.error.exception.ErrorCode;

public class ExpiredTokenException extends BusinessException {

        public ExpiredTokenException() {
            super(ErrorCode.EXPIRED_TOKEN);
        }
}
