package com.example.board.global.error;

import com.example.board.global.error.exception.BusinessException;
import com.example.board.global.error.exception.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorCode> handleBusinessException(BusinessException e) {
        return ResponseEntity.badRequest().body(e.getErrorCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorCode> handleException(Exception e) {
        ErrorCode errorResponse = ErrorCode.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorCode> handleAccessDeniedException(AccessDeniedException e) {
        ErrorCode errorResponse = ErrorCode.HANDLE_ACCESS_DENIED;
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorCode> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        ErrorCode errorResponse = ErrorCode.METHOD_NOT_ALLOWED;
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorCode> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        ErrorCode errorResponse = ErrorCode.INVALID_TYPE_VALUE;
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }
}
