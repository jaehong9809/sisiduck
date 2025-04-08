package com.a702.finafanbe.core.identify.dto.request;

import com.a702.finafanbe.global.common.financialnetwork.header.BaseRequestHeaderIncludeUserKey;

public record KRW1CertificationValidateRequest(
    BaseRequestHeaderIncludeUserKey Header,
    String accountNo,
    String authText,
    String authCode
) {
    public static KRW1CertificationValidateRequest of(
            BaseRequestHeaderIncludeUserKey Header,
            String accountNo,
            String authText,
            String authCode
    ) {
        return new KRW1CertificationValidateRequest(
                Header,
                accountNo,
                authText,
                authCode
        );
    }
}
