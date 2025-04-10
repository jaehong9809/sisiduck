package com.a702.finafan.data.funding.dto.response

import com.a702.finafan.data.funding.dto.UsageDto
import com.a702.finafan.data.funding.dto.toDomain
import com.a702.finafan.domain.funding.model.Form

data class FormResponse(
    val content: String,
    val usages: List<UsageDto>,
    val images: List<String>
)

fun FormResponse.toDomain(): Form {
    return Form(
        content = content,
        usageList = usages.map { it.toDomain() },
        imageList = images
    )
}