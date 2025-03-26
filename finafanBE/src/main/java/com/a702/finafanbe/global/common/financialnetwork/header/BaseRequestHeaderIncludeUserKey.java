package com.a702.finafanbe.global.common.financialnetwork.header;

import com.a702.finafanbe.global.common.financialnetwork.entity.FinancialNetworkUtil;
import com.a702.finafanbe.global.common.util.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseRequestHeaderIncludeUserKey extends BaseRequestHeader {
    private String userKey;
}
