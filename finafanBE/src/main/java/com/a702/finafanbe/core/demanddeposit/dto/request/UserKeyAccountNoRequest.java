package com.a702.finafanbe.core.demanddeposit.dto.request;

import com.a702.finafanbe.global.common.financialnetwork.header.BaseRequestHeaderIncludeUserKey;

public record UserKeyAccountNoRequest(
    BaseRequestHeaderIncludeUserKey Header,
    String accountNo
){
}
