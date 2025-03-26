package com.a702.finafanbe.core.savings.dto.apidto;

import com.a702.finafanbe.core.savings.dto.DepositRequest;
import com.a702.finafanbe.global.common.financialnetwork.header.BaseRequestHeaderIncludeUserKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ApiDepositRequest {

    private BaseRequestHeaderIncludeUserKey header;
    private String depositAccountNo;
    private Long transactionBalance;
    private String withdrawalAccountNo;
    // 입금 계좌에 작성되는 거래 요약 내용
    private String depositTransactionSummary;
    // 출금 계좌에 작성되는 거래 요약 내용
    private String withdrawalTransactionSummary;

    public static ApiDepositRequest create(DepositRequest request) {
        return ApiDepositRequest.builder()
                                .header(request.getHeader())
                                .depositAccountNo(request.getDepositAccountNo())
                                .build();
    }
}
