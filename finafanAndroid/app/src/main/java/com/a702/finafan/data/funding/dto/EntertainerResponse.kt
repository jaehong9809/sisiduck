package com.a702.finafan.data.funding.dto

import com.a702.finafan.domain.funding.model.Star

data class EntertainerResponse (
    val entertainerId: Long,
    val name: String,
    val imageUrl: String
)

fun EntertainerResponse.toDomain() : Star {
    return Star(
        id = this.entertainerId,
        name = this.name,
        index = 0,
        image = this.imageUrl,
        thumbnail = null
    )
}
