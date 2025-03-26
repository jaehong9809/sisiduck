package com.a702.finafanbe.core.savings.entity;

import lombok.Getter;

@Getter
public enum AccountUniqueNo {
    PERSONAL(""),
    FUNDING("");

    private final String code;

    AccountUniqueNo(String code) {
        this.code = code;
    }

}