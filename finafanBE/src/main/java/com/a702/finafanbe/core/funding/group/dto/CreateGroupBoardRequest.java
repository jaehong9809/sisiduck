package com.a702.finafanbe.core.funding.group.dto;

import java.util.List;

public record CreateGroupBoardRequest(
    String content,
    List<AmountDto> amounts,
    List<String> imageUrl
) {

}
