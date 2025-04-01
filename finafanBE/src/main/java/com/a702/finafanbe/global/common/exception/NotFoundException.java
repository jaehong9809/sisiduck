package com.a702.finafanbe.global.common.exception;

import com.a702.finafanbe.global.common.response.ResponseData;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotFoundException extends GlobalException {
    public NotFoundException(ResponseData<?> error) {
        super(error, HttpStatus.NOT_FOUND);
    }
}

