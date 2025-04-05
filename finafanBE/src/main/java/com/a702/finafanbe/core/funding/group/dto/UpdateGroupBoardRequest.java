package com.a702.finafanbe.core.funding.group.dto;

public record UpdateGroupBoardRequest(
        String title,
        String content,
        Long amount,
        String imgUrl
) {
}
