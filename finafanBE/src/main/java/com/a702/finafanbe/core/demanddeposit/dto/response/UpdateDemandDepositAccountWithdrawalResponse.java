package com.a702.finafanbe.core.demanddeposit.dto.response;

import com.a702.finafanbe.global.common.financialnetwork.header.BaseResponseHeader;
import lombok.Getter;

public record UpdateDemandDepositAccountWithdrawalResponse (
    BaseResponseHeader Header,
    REC REC
) {
    @Getter
    public static class REC {
        private String transactionUniqueNo;
        private String transactionDate;
    }
}