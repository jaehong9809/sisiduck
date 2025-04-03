package com.a702.finafan.domain.funding.model

enum class FundingFilter(val value: String) {
    ALL("all"),
    MY("my"),
    PARTICIPATED("participated");

    override fun toString(): String {
        return value
    }
}