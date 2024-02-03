package com.example.board.domain.post.exception;

import com.example.board.global.error.exception.BusinessException;
import com.example.board.global.error.exception.ErrorCode;

public class NotFoundPostException extends BusinessException {
    public NotFoundPostException() {
        super(ErrorCode.NOT_FOUND_POST);
    }
}
