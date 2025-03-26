package com.a702.finafanbe.core.demanddeposit.dto.request;

import com.a702.finafanbe.global.common.financialnetwork.header.BaseRequestHeader;

public record RegisterDemandDepositRequest(
        BaseRequestHeader Header,
         String bankCode,
         String accountName,
         String accountDescription
){
    public static RegisterDemandDepositRequest of(
            BaseRequestHeader Header,
            String bankCode,
            String accountName,
            String accountDescription
    ){
        return new RegisterDemandDepositRequest(
                Header,
                bankCode,
                accountName,
                accountDescription
        );
    }
}
