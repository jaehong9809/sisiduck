package com.a702.finafanbe.core.savings.dto;

import com.a702.finafanbe.global.common.financialnetwork.header.BaseRequestHeaderIncludeUserKey;
import lombok.Getter;

@Getter
public class CreateSavingAccountRequest {

    private BaseRequestHeaderIncludeUserKey header;

    private Long entertainerId;

    private String accountNickname;

    private String withdrwalAccount;

    private String accountTypeUniqueNo;
}
