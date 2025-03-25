package com.a702.finafanbe.core.s3.exception;


import com.a702.finafanbe.global.common.exception.ErrorCode;
import com.a702.finafanbe.global.common.exception.MinorException;
import com.a702.finafanbe.global.common.response.ResponseData;

public class S3Exception {

    public static class S3UploadException extends MinorException {
        public S3UploadException(ErrorCode errorCode) {
            super(ResponseData.builder()
                    .code(errorCode.getCode())
                    .message(errorCode.getMessage())
                    .build());
        }
    }

}
