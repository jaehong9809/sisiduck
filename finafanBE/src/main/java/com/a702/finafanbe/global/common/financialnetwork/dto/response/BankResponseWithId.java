package com.a702.finafanbe.global.common.financialnetwork.dto.response;

public record BankResponseWithId(
        String bankCode,
        String bankName,
        Long bankId
) {}