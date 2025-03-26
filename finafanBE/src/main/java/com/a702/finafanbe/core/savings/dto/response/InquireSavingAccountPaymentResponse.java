package com.a702.finafanbe.core.savings.dto.response;

import com.a702.finafanbe.global.common.financialnetwork.header.BaseResponseHeaderIncludeInstitutionCode;
import java.util.List;

public record InquireSavingAccountPaymentResponse(
        BaseResponseHeaderIncludeInstitutionCode Header,
        REC REC
) {

    private record REC(
        String bankCode,
        String bankName,
        String accountNo,
        String accountName,
        Double interestRate,
        Long depositBalance,
        Long totalBalance,
        String accountCreateDate,
        String accountExpiryDate,
        List<PaymentInfo> paymentInfo,
        String depositInstallment
    ){}

    private record PaymentInfo(
        String depositInstallment,
        Long paymentBalance,
        String paymentDate,
        String paymentTime,
        String status,
        String failureReason
    ){}
}
