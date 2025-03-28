package com.a702.finafanbe.core.demanddeposit.dto.response;

import java.util.List;

public record InquireEntertainerHistoriesResponse(
        String totalCount,
        List<AccountTransactionHistoriesResponse.list> list,
        String imageUrl
) {
    public static InquireEntertainerHistoriesResponse of(
            String totalCount,
            List<AccountTransactionHistoriesResponse.list> list,
            String imageUrl
    ){
        return new InquireEntertainerHistoriesResponse(
                totalCount,
                list,
                imageUrl
        );
    }
}
