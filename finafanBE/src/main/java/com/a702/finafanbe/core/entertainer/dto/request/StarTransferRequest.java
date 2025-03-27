package com.a702.finafanbe.core.entertainer.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record StarTransferRequest(
        String userEmail,
        String depositAccountNo,
        String depositTransactionSummary,
        Long transactionBalance,
        String withdrawalAccountNo,
        String withdrawalTransactionSummary,
        String message,
        MultipartFile imageFile
) {
}
