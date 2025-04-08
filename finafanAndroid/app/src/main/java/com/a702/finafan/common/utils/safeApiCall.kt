package com.a702.finafan.common.utils

import com.a702.finafan.common.domain.DataResource
import com.a702.finafan.common.domain.ExceptionHandler

suspend fun <T> safeApiCall(
    block: suspend () -> T
): DataResource<T> {
    return try {
        DataResource.success(block())
    } catch (e: Throwable) {
        DataResource.error(ExceptionHandler.handle(e))
    }
}
