package com.a702.finafanbe.core.funding.funding.dto;

public record UpdateFundingDescriptionRequest(
        String description
) {
    public static UpdateFundingDescriptionRequest of (
            String description
    ) {
        return new UpdateFundingDescriptionRequest(
                description
        );
    }
}
