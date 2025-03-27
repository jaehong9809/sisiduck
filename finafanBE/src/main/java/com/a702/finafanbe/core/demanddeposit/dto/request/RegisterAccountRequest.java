package com.a702.finafanbe.core.demanddeposit.dto.request;

import com.a702.finafanbe.global.common.financialnetwork.header.BaseRequestHeader;

public record RegisterAccountRequest(
        BaseRequestHeader Header,
         String bankCode,
         String accountName,
         String accountDescription
){
    public static RegisterAccountRequest of(
            BaseRequestHeader Header,
            String bankCode,
            String accountName,
            String accountDescription
    ){
        return new RegisterAccountRequest(
                Header,
                bankCode,
                accountName,
                accountDescription
        );
    }
}
