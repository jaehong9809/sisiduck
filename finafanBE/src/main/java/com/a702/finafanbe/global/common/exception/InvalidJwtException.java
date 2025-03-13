package com.a702.finafanbe.global.common.exception;

import lombok.Getter;

@Getter
public class InvalidJwtException extends RuntimeException {

    private int code;
    private String message;

    public InvalidJwtException(final int code, final String message) {
        this.code = code;
        this.message = message;
    }
}
