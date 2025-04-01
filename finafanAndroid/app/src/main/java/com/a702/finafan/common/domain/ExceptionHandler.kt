package com.a702.finafan.common.domain

import retrofit2.HttpException
import java.io.IOException

object ExceptionHandler {
    fun handle(e: Exception): String {
        return when (e) {
            is IOException -> "인터넷 연결을 확인해주세요."
            is HttpException -> when (e.code()) {
                401 -> "로그인이 필요합니다."
                500 -> "서버 오류가 발생했습니다."
                else -> "네트워크 오류가 발생했습니다."
            }
            else -> "알 수 없는 오류가 발생했습니다."
        }
    }
}