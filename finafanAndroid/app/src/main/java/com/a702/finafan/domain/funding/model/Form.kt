package com.a702.finafan.domain.funding.model

data class Form(
    val content: String,
    val usageList: List<UsageItem>,
    val imageList: List<String>
)

data class UsageItem(
    val content: String,
    val amount: Long
)