package com.a702.finafanbe.core.identify.dto.request;

import com.a702.finafanbe.global.common.financialnetwork.header.BaseRequestHeaderIncludeUserKey;

public record KRW1CertificationValidateRequest(
    BaseRequestHeaderIncludeUserKey Header,
    String accountNo,
    String authText,
    String authCode
) {

}
