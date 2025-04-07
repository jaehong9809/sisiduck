package com.a702.finafanbe.core.funding.funding.dto;

import java.time.LocalDate;

public record CreateFundingRequest(
        String accountNickname,
        Long entertainerId,
        LocalDate fundingExpiryDate,
        String description,
        Long goalAmount
) {
    public static CreateFundingRequest of(
            String accountNickname,
            Long entertainerId,
            LocalDate fundingExpiryDate,
            String description,
            Long goalAmount
    ) {
        return new CreateFundingRequest(
                accountNickname,
                entertainerId,
                fundingExpiryDate,
                description,
                goalAmount
        );
    }

}
