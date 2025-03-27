package com.a702.finafan.data.savings.api

import com.a702.finafan.common.data.dto.ApiResponse
import com.a702.finafan.data.savings.dto.response.StarResponse
import retrofit2.http.GET

interface SavingApi {
    @GET("v1/star")
    suspend fun getStars(): ApiResponse<List<StarResponse>>

    // TODO: 적금 - 입금하기

    // TODO: 적금 입금내역 조회

    // TODO: 적금 계좌 정보 조회

    // TODO: 입금하기

    // TODO: 출금 계좌번호 목록 조회
}