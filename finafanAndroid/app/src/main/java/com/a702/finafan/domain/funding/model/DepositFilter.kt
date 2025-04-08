package com.a702.finafan.domain.funding.model

enum class DepositFilter(val value: String) {
    ALL("all"),
    MY("my");

    override fun toString(): String {
        return value
    }
}