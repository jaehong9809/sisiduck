package com.a702.finafanbe.core.funding.group.dto;

import lombok.Getter;

@Getter
public class CreateGroupBoardRequest {

    private String title;

    private String content;

    private Long amount;

    private String imgUrl;
}
