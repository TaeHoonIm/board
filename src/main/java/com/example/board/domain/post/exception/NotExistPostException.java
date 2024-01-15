package com.example.board.domain.post.exception;

import com.example.board.global.error.exception.BusinessException;
import com.example.board.global.error.exception.ErrorCode;

public class NotExistPostException extends BusinessException {
    public NotExistPostException() {
        super(ErrorCode.NOT_EXIST_POST);
    }
}
