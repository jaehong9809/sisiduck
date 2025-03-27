package com.a702.finafan.data.savings.api

import com.a702.finafan.common.data.dto.ApiResponse
import com.a702.finafan.data.savings.dto.StarResponse
import retrofit2.http.GET

interface SavingApi {
    @GET("v1/star")
    suspend fun getStars(): ApiResponse<List<StarResponse>>
}