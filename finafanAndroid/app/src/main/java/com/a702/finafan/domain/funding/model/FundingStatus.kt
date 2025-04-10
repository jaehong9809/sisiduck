package com.a702.finafan.domain.funding.model


enum class FundingStatus(val value: String) {
    INPROGRESS("진행 중"),
    CANCELED("진행 무산"),
    FAILED("미달성"),
    SUCCESS("달성 완료"),
    TERMINATED("모금 종료");

    override fun toString(): String {
        return value
    }
}