package com.a702.finafanbe.core.savings.dto.fundingDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetFundingResponse {
    private EntertainerResponse entertainer;
    private String fundingName;
    private Long currentAmount;
    private Long goalAmount;
    private LocalDateTime fundingExpiryDate;
}
