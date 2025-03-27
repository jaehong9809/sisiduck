package com.a702.finafanbe.core.savings.dto.fundingDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FundingApplicationResponse {

    private String name;

    private Long balance;

    private String content;

    private LocalDateTime createdAt;

}
