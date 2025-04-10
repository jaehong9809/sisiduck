package com.a702.finafan.domain.link.model

data class ExtractedLinksResult(
    val cleanText: String,
    val links: List<String>
)
