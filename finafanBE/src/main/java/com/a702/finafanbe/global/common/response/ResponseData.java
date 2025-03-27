package com.a702.finafanbe.global.common.response;

import com.a702.finafanbe.global.common.exception.ErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Builder @Data
@NoArgsConstructor @AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseData<T> {

    // 결과 코드
    @JsonProperty("code")
    private String code;

    // 결과 메시지
    @JsonProperty("message")
    private String message;

    // 결과 데이터
    @JsonProperty("data")
    private T data = null;

    public static ResponseData createResponse(ErrorCode errorCode){
        return ResponseData.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
    }
}
