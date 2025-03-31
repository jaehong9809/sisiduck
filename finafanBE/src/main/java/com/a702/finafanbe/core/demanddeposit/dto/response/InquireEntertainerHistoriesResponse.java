package com.a702.finafanbe.core.demanddeposit.dto.response;

import com.a702.finafanbe.core.demanddeposit.dto.request.TransactionWithImageResponse;

import java.util.List;

public record InquireEntertainerHistoriesResponse(
        String totalCount,
        List<TransactionWithImageResponse> transactions
) {
    public static InquireEntertainerHistoriesResponse of(
            String totalCount,
            List<TransactionWithImageResponse> transactions
    ) {
        return new InquireEntertainerHistoriesResponse(
                totalCount,
                transactions
        );
    }
}
