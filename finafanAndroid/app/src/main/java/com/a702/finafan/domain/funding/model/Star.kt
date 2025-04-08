package com.a702.finafan.domain.funding.model

data class Star (
    val id: Long,
    val name: String,
    val index: Int,
    val image: String? = null,
    val thumbnail: String? = null
)