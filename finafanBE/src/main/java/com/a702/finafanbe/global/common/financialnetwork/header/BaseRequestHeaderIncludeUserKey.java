package com.a702.finafanbe.global.common.financialnetwork.header;

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
