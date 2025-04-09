package com.a702.finafanbe.core.funding.group.dto;

import java.util.List;

public record GetGroupBoardResponse(
        String content,
        List<AmountDto> amounts,
        List<String> imageUrl
) {
    public static GetGroupBoardResponse of(
            String content,
            List<AmountDto> amounts,
            List<String> imageUrl
    ) {
        return new GetGroupBoardResponse(
                content,
                amounts,
                imageUrl
        );
    }
}
