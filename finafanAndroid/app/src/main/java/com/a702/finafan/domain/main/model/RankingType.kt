package com.a702.finafan.domain.main.model

import com.a702.finafan.domain.main.model.RankingType.entries


enum class RankingType(val value: String) {
    TOTAL("total"),
    WEEKLY("weekly"),
    DAILY("daily");

    companion object {
        fun fromValue(value: String): RankingType? {
            return entries.firstOrNull { it.value == value.lowercase() }
        }
    }
}
