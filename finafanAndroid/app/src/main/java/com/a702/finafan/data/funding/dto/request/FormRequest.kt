package com.a702.finafan.data.funding.dto.request

import com.a702.finafan.data.funding.dto.UsageDto
import com.a702.finafan.data.funding.dto.toData
import com.a702.finafan.domain.funding.model.Form

data class FormRequest(
    val content: String,
    val amounts: List<UsageDto>,
    val imageUrl: List<String>
)

fun Form.toData(): FormRequest {
    return FormRequest(
        content = content,
        amounts = usageList.map { it.toData() },
        imageUrl = imageList,
    )
}