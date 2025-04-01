package com.a702.finafan.data.main.api

import com.a702.finafan.common.data.dto.ApiResponse
import com.a702.finafan.data.main.dto.MainRankingResponse
import com.a702.finafan.data.main.dto.MainSavingResponse
import retrofit2.http.GET

interface MainApi {
    @GET("v1/star/home")
    suspend fun getMainSavingList(): ApiResponse<List<MainSavingResponse>>

    @GET("v1/ranking/daily/entertainers")
    suspend fun getDailyTop3(): ApiResponse<List<MainRankingResponse>>

    @GET("v1/ranking/weekly/entertainers")
    suspend fun getWeeklyTop3(): ApiResponse<List<MainRankingResponse>>

    // TODO: API 연결
    suspend fun getTotalTop3(): ApiResponse<List<MainRankingResponse>>
}