package com.a702.finafan.data.ble.dto.response

import com.a702.finafan.domain.ble.model.Fan

data class MatchFansResponse(
    val userId: Long,
    val name: String,
    val profileImageUrl: String
)

fun MatchFansResponse.toDomain(): Fan = Fan(
    id = userId,
    name = name,
    profileUrl = profileImageUrl
)