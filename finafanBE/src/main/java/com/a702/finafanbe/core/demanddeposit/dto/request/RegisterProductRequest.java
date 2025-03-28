package com.a702.finafanbe.core.demanddeposit.dto.request;

import com.a702.finafanbe.global.common.financialnetwork.header.BaseRequestHeader;

public record RegisterProductRequest(
        BaseRequestHeader Header,
         String bankCode,
         String accountName,
         String accountDescription
){
    public static RegisterProductRequest of(
            BaseRequestHeader Header,
            String bankCode,
            String accountName,
            String accountDescription
    ){
        return new RegisterProductRequest(
                Header,
                bankCode,
                accountName,
                accountDescription
        );
    }
}
