package com.a702.finafanbe.core.savings.dto.APIdto;

import com.a702.finafanbe.core.savings.dto.CreateSavingAccountRequest;
import com.a702.finafanbe.global.common.header.BaseRequestHeaderIncludeUserKey;

public class APICreateAccountRequest(
        BaseRequestHeaderIncludeUserKey header,
        String accountTypeUniqueNo
) {
    public static APICreateAccountRequest of(CreateSavingAccountRequest request, BaseRequestHeaderIncludeUserKey header) {
        return new APICreateAccountRequest(header, request.getAccountTypeUniqueNo());
    }
}