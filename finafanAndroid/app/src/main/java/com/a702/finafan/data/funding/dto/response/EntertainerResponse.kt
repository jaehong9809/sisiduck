package com.a702.finafan.data.funding.dto.response

import com.a702.finafan.domain.funding.model.Star

data class EntertainerResponse (
    val entertainerId: Long,
    val name: String,
    val imageUrl: String,
    val thumbnailUrl: String
)

fun EntertainerResponse.toDomain() : Star {
    return Star(
        id = this.entertainerId,
        name = this.name,
        index = 0,
        image = this.imageUrl,
        thumbnail = this.thumbnailUrl
    )
}
