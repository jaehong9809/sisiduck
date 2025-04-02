package com.a702.finafan.common.domain

import android.util.Log
import com.a702.finafan.common.data.dto.ErrorResponse
import com.google.gson.Gson
import retrofit2.HttpException
import java.io.IOException

object ExceptionHandler {
    fun handle(e: Exception): String {
        Log.d("exception handler", e.toString())

        return when (e) {
            is IOException -> "인터넷 연결을 확인해주세요."
            is HttpException -> {
                val errorMessage = extractErrorMessage(e)

                errorMessage ?: when (e.code()) {
                    400 -> "잘못된 요청입니다. 입력값을 확인해주세요."
                    401 -> "로그인이 필요합니다."
                    500 -> "서버 오류가 발생했습니다."
                    else -> "네트워크 오류가 발생했습니다."
                }
            }
            else -> "알 수 없는 오류가 발생했습니다."
        }
    }

    private fun extractErrorMessage(e: HttpException): String? {
        return try {
            val errorResponse =
                Gson().fromJson(e.response()?.errorBody()?.string(), ErrorResponse::class.java)

            errorResponse.message
        } catch (ex: Exception) {
            null
        }
    }
}