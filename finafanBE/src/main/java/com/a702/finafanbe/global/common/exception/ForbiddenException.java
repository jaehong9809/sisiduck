package com.a702.finafanbe.global.common.exception;

import com.a702.finafanbe.global.common.response.ResponseData;
import org.springframework.http.HttpStatus;

public class ForbiddenException extends GlobalException {
    public ForbiddenException(ResponseData<?> error) {
        super(error, HttpStatus.FORBIDDEN);
    }
}
