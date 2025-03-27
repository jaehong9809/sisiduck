package com.a702.finafanbe.core.savings.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetFundingAdminResponse {

    private String adminName;

    private String description;

    private int fundingCount;

    private int fundingSuccessCount;

    public GetFundingAdminResponse(String adminName, String description, Long fundingCount, Long fundingSuccessCount) {
        this.adminName = adminName;
        this.description = description;
        this.fundingCount = fundingCount != null ? fundingCount.intValue() : 0;
        this.fundingSuccessCount = fundingSuccessCount != null ? fundingSuccessCount.intValue() : 0;
    }
}
