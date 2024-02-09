package org.devridge.api.exception;

import org.devridge.api.exception.common.BaseException;
import org.devridge.common.dto.BaseErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseErrorResponse> handleBaseException(BaseException exception) {
        return ResponseEntity
            .status(exception.getCode())
            .body(new BaseErrorResponse(exception.getMessage()));
    }
}