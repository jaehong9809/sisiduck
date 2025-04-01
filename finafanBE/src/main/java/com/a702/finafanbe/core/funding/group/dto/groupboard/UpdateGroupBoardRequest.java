package com.a702.finafanbe.core.funding.group.dto.groupboard;

public record UpdateGroupBoardRequest(
        String title,
        String content,
        Long amount,
        String imgUrl
) {
}
