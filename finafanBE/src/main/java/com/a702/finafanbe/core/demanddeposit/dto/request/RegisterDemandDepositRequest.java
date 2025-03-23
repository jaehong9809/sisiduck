package com.a702.finafanbe.core.demanddeposit.dto.request;

import com.a702.finafanbe.global.common.header.BaseRequestHeader;
import com.a702.finafanbe.global.common.header.RequestHeader;
import lombok.Getter;

public record RegisterDemandDepositRequest(
        BaseRequestHeader Header,
         String bankCode,
         String accountName,
         String accountDescription
){
}
