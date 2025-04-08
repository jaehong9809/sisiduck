package com.a702.finafan.common.domain

class HandledException(
    userMessage: String,
    cause: Throwable? = null
) : Exception(userMessage, cause)
