package com.a702.finafanbe.core.savings.dto;

import com.a702.finafanbe.global.common.financialnetwork.header.BaseRequestHeaderIncludeUserKey;
import lombok.Getter;

@Getter
public class DepositRequest {

    private BaseRequestHeaderIncludeUserKey header;
    private String depositAccountNo;
    private Long transactionBalance;
    private String withdrawalAccountNo;
    // 입금 계좌에 작성되는 거래 요약 내용
    private String depositTransactionSummary;
}
