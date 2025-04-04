package com.a702.finafanbe.core.funding.funding.entity;

import lombok.Getter;

@Getter
public enum FundingStatus {
    INPROGRESS,
    CANCELED,
    FAILED,
    SUCCESS,
    TERMINATED
}
