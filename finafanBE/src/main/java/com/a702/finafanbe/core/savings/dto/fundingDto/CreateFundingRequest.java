package com.a702.finafanbe.core.savings.dto.fundingDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateFundingRequest {

    private String accountNickname;

    private Long entertainerId;

    private LocalDateTime fundingExpiryDate;

    private String description;

    private Long goalAmount;
}
