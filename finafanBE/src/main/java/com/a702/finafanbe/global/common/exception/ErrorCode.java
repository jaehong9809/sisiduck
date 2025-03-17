package com.a702.finafanbe.global.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    INVALID_REQUEST(1000, "유효하지 않은 요청입니다."),
    NotFoundUser(1001, "유저를 찾을 수 없습니다."),

    INVALID_REFRESH_TOKEN(2000,"레프리시 토큰이 올바르지 유효하지 않습니다"),

    ;

    private final int code;
    private final String message;
}
