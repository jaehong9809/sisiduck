package com.a702.finafanbe.global.common.exception;

import com.a702.finafanbe.global.common.response.ResponseData;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidJwtException extends GlobalException {

    public InvalidJwtException(ResponseData<?> error) {
        super(error, HttpStatus.FORBIDDEN);
    }
}
