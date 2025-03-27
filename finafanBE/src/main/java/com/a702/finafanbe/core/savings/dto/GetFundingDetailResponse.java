package com.a702.finafanbe.core.savings.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetFundingDetailResponse {

    private boolean participated;

    private EntertainerResponse entertainer;

    private GetFundingAdminResponse adminUser;

    private Long goalAmount;

    private Long currentAmount;

    private LocalDateTime fundingExpiryDate;

    private List<FundingApplicationResponse> fundingApplication;

    public void setFundingApplication(List<FundingApplicationResponse> list) {
        this.fundingApplication = list;
    }

    public void setParticipated(Boolean participated) {
        this.participated = participated;
    }

    public GetFundingDetailResponse(
            EntertainerResponse entertainer,
            GetFundingAdminResponse adminUser,
            Long goalAmount,
            Long currentAmount,
            LocalDateTime fundingExpiryDate,
            List<FundingApplicationResponse> fundingApplication
    ) {
        this.entertainer = entertainer;
        this.adminUser = adminUser;
        this.goalAmount = goalAmount;
        this.currentAmount = currentAmount;
        this.fundingExpiryDate = fundingExpiryDate; // LocalDate → LocalDateTime 변환
        this.fundingApplication = fundingApplication;
    }
}
