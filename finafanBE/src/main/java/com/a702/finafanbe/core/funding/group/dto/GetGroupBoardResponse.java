package com.a702.finafanbe.core.funding.group.dto;

import java.util.List;

public record GetGroupBoardResponse(
        Long id,
        String content,
        List<AmountDto> amounts,
        List<String> imageUrl
) {
    public static GetGroupBoardResponse of(
            Long id,
            String content,
            List<AmountDto> amounts,
            List<String> imageUrl
    ) {
        return new GetGroupBoardResponse(
                id,
                content,
                amounts,
                imageUrl
        );
    }
}
