package com.a702.finafanbe.core.funding.group.dto;

public record GetGroupBoardDetailResponse(
        Long id,
        String title,
        String content,
        Long amount,
        String imgUrl
) {
    public static GetGroupBoardDetailResponse of(
            Long id,
            String title,
            String content,
            Long amount,
            String imgUrl
    ) {
        return new GetGroupBoardDetailResponse(
                id,
                title,
                content,
                amount,
                imgUrl);
    }
}
