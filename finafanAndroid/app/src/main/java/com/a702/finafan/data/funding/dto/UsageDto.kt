package com.a702.finafan.data.funding.dto

import com.a702.finafan.domain.funding.model.UsageItem

data class UsageDto(
    val content: String,
    val amount: Long
)

fun UsageItem.toData(): UsageDto {
    return UsageDto(
        content = content,
        amount = amount
    )
}

fun UsageDto.toDomain(): UsageItem {
    return UsageItem(
        content = content,
        amount = amount
    )
}