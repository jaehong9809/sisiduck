package com.a702.finafan.data.funding.api

import com.a702.finafan.common.data.dto.ApiResponse
import com.a702.finafan.data.funding.dto.FundingDetailResponse
import com.a702.finafan.data.funding.dto.FundingResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FundingApi {
    @GET("v1/fundings")
    suspend fun getFundingList(
        @Query("filter") filter: String
    ): ApiResponse<List<FundingResponse>>

//    @GET("v1/fundings")
//    suspend fun getParticipatingFundingList(): ApiResponse<List<FundingResponse>>
//
//    @GET("v1/fundings")
//    suspend fun getMyFundingList(): ApiResponse<List<FundingResponse>>

    @GET("v1/fundings/{fundingGroupId}")
    suspend fun getFundingDetail(@Path("fundingGroupId") fundingId: Long): ApiResponse<FundingDetailResponse>

    @GET("v1/fundings/{fundingGroupId}/join")
    suspend fun joinFunding(@Path("fundingGroupId") fundingId: Long): ApiResponse<Void>
}