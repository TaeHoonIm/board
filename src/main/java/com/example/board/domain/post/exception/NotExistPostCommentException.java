package com.example.board.domain.post.exception;

import com.example.board.global.error.exception.BusinessException;
import com.example.board.global.error.exception.ErrorCode;

public class NotExistPostCommentException extends BusinessException {
    public NotExistPostCommentException() {
        super(ErrorCode.NOT_EXIST_POST_COMMENT);
    }
}
