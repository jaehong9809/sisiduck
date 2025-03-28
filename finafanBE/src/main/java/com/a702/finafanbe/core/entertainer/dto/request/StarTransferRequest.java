package com.a702.finafanbe.core.entertainer.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record StarTransferRequest(
        String userEmail,
        Long depositAccountId,
        Long transactionBalance,
        String message,
        MultipartFile imageFile
) {
}
