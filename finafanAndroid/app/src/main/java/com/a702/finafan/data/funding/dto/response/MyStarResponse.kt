package com.a702.finafan.data.funding.dto.response

import com.a702.finafan.domain.funding.model.MyStar

data class MyStarResponse (
    val entertainerId: Long,
    val entertainerName: String,
    val entertainerProfileUrl: String,
    val fandomName: String
)

fun MyStarResponse.toDomain(): MyStar {
    return MyStar(
        id = this.entertainerId,
        name = this.entertainerName,
        imageUrl = this.entertainerProfileUrl
    )
}