package com.a702.finafan.common.domain

import com.a702.finafan.common.data.dto.ErrorResponse
import com.google.gson.Gson
import retrofit2.HttpException
import java.io.IOException

object ExceptionHandler {
    fun handle(e: Throwable): Throwable {
        return when (e) {
            is IOException -> HandledException("인터넷 연결을 확인해주세요.", e)
            is HttpException -> {
                val errorMessage = extractErrorMessage(e)
                HandledException(errorMessage ?: getDefaultHttpMessage(e.code()), e)
            }
            else -> HandledException("오류가 발생했습니다.", e)
        }
    }

    private fun getDefaultHttpMessage(code: Int): String {
        return when (code) {
            400 -> "잘못된 요청입니다. 입력값을 확인해주세요."
            401 -> "로그인이 필요합니다."
            500 -> "서버 오류가 발생했습니다."
            else -> "네트워크 오류가 발생했습니다."
        }
    }

    private fun extractErrorMessage(e: HttpException): String? {
        return try {
            val errorResponse =
                Gson().fromJson(e.response()?.errorBody()?.string(), ErrorResponse::class.java)
            if (errorResponse.code == "E8007") {
                "스타 적금과 연결된 계좌입니다."
            } else {
                errorResponse.message
            }
        } catch (ex: Exception) {
            null
        }
    }
}
