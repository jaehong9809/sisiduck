package com.a702.finafanbe.core.savings.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ParticipateFundingRequest {

    private Long accountId;

    private Long balance;

    private String content;
}
