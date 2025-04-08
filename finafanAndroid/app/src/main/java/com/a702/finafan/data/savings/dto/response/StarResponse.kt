package com.a702.finafan.data.savings.dto.response

import com.a702.finafan.domain.savings.model.Star

data class StarResponse(
    val entertainerId: Long = 0,
    val entertainerName: String = "",
    val birthDate: String = "",
    val entertainerProfileUrl: String = "",
    val fandomName: String = ""
)

fun StarResponse.toDomain(): Star {
    return Star(
        starId = this.entertainerId,
        starName = this.entertainerName,
        birthDate = this.birthDate,
        starImageUrl = this.entertainerProfileUrl,
        fandomName = this.fandomName
    )
}