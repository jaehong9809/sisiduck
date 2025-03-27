package com.a702.finafanbe.core.entertainer.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record StarDepositRequest(
        String userEmail,
        String accountNo,
        Long transactionBalance,
        String transactionSummary,
        String message,
        MultipartFile imageFile
) {
}
