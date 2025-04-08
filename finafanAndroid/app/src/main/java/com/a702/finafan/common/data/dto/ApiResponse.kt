package com.a702.finafan.common.data.dto

data class ApiResponse<T>(
    val code: String,
    val message: String,
    val data: T?
)

inline fun <T, R> ApiResponse<T>.getOrThrow(mapper: (T) -> R): R {
    if (code == "S0000" && data != null) {
        return mapper(data)
    } else {
        throw Exception(message)
    }
}

inline fun <T, R> ApiResponse<T>.getOrThrowNull(mapper: (T?) -> R): R {
    if (code == "S0000") {
        return mapper(data)
    } else {
        throw Exception(message)
    }
}