package com.a702.finafan.common.data.dto

import com.a702.finafan.common.domain.DataResource
import com.a702.finafan.common.domain.HandledException

data class ApiResponse<T>(
    val code: String,
    val message: String,
    val data: T?
)

inline fun <T, R> ApiResponse<T>.getOrDataResource(mapper: (T) -> R): DataResource<R> {
    return if (code == "S0000" && data != null) {
        DataResource.success(mapper(data))
    } else {
        DataResource.error(HandledException(message))
    }
}
