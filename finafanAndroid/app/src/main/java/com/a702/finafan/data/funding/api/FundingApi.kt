package com.a702.finafan.data.funding.api

import com.a702.finafan.data.funding.dto.FundingDetailResponse
import com.a702.finafan.data.funding.dto.FundingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FundingApi {
    @GET("fundings")
    suspend fun getFundingList(): List<FundingResponse>

    @GET("fundings/{fundingGroupId}")
    suspend fun getFundingDetail(@Path("fundingGroupId") fundingId: Long): FundingDetailResponse

    @POST("fundings/{fundingGroupId}")
    suspend fun joinFunding(@Path("fundingGroupId") fundingId: Long): Response<Void>
}