package com.example.board.global.error.exception;

public class HandleAccessException extends BusinessException{
    public HandleAccessException() {
        super(ErrorCode.HANDLE_ACCESS_DENIED);
    }
}
