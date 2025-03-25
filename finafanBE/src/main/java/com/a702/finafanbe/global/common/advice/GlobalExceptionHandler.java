package com.a702.finafanbe.global.common.advice;

import com.a702.finafanbe.global.common.exception.BadRequestException;
import com.a702.finafanbe.global.common.exception.ErrorCode;
import com.a702.finafanbe.global.common.exception.ExceptionResponse;
import com.a702.finafanbe.global.common.exception.InvalidJwtException;
import com.a702.finafanbe.global.common.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ){

        log.warn(ex.getMessage(), ex);
        String message = Objects.requireNonNull(ex.getBindingResult().getFieldError())
                .getDefaultMessage();

        return ResponseEntity.badRequest()
                .body(new ExceptionResponse(ErrorCode.SYSTEM_ERROR.getCode(), message));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequestException(BadRequestException e){

        log.warn(e.getMessage(), e);

        return ResponseUtil.failure(e);
    }

//    @ExceptionHandler(SocialLoginException.class)
//    public ResponseEntity<ExceptionResponse> handleSocialLoginException(SocialLoginException e){
//        log.warn(e.getMessage(), e);
//
//        return ResponseEntity.badRequest()
//                .body(new ExceptionResponse(e.getCode(), e.getMessage()));
//    }

    @ExceptionHandler(InvalidJwtException.class)
    public ResponseEntity<?> handleInvalidJwtException(InvalidJwtException e){
        log.warn(e.getMessage(), e);
        return ResponseUtil.failure(e);
    }
}
