package com.a702.finafan.common.data.dto

data class ApiResponse<T>(
    val code: String,
    val message: String,
    val data: T?
)