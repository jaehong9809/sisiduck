package com.a702.finafanbe.core.savings.dto;

import lombok.Getter;

@Getter
public class CreateSavingAccountRequest {
    private Long entertainerId;
    private String accountNickname;
    private String withdrawlAccount;
    private String accountTypeUniqueNo;
}
