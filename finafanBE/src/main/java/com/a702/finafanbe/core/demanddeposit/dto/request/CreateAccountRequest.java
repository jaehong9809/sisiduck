package com.a702.finafanbe.core.demanddeposit.dto.request;


import com.a702.finafanbe.global.common.header.BaseRequestHeaderIncludeUserKey;

public record CreateAccountRequest (
    BaseRequestHeaderIncludeUserKey Header,
    String accountTypeUniqueNo
){

}
