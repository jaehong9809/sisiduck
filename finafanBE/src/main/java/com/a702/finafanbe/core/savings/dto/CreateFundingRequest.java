package com.a702.finafanbe.core.savings.dto;

import com.a702.finafanbe.global.common.financialnetwork.header.BaseRequestHeaderIncludeUserKey;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateFundingRequest {

    private String accountNickname;

    private Long entertainerId;

    private LocalDateTime fundingExpiryDate;

    private String description;

    private Long goalAmount;

    private String accountTypeUniqueNo;
}
