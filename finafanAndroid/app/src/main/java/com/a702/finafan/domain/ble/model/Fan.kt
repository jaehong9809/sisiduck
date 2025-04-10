package com.a702.finafan.domain.ble.model

import java.util.UUID

data class Fan(
    val id: UUID,
    val name: String,
    val profileUrl: String
)