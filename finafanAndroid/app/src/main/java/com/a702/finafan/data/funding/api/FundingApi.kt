package com.a702.finafan.data.funding.api

import com.a702.finafan.common.data.dto.ApiResponse
import com.a702.finafan.data.funding.dto.request.StartFundingRequest
import com.a702.finafan.data.funding.dto.response.DepositResponse
import com.a702.finafan.data.funding.dto.response.FundingDetailResponse
import com.a702.finafan.data.funding.dto.response.FundingResponse
import com.a702.finafan.data.funding.dto.response.MyStarResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface FundingApi {
    @GET("v1/fundings")
    suspend fun getFundingList(
        @Query("filter") filter: String
    ): ApiResponse<List<FundingResponse>>

    @GET("v1/fundings/{fundingId}")
    suspend fun getFundingDetail(
        @Path("fundingId") fundingId: Long)
    : ApiResponse<FundingDetailResponse>

    @GET("v1/fundings/{fundingId}/transaction")
    suspend fun getFundingDepositHistory(
        @Path("fundingId") fundingId: Long,
        @Query("filter") filter: String
    ): ApiResponse<List<DepositResponse>>

    @POST("v1/fundings")
    suspend fun startFunding(
        @Body request: StartFundingRequest
    ): ApiResponse<Void>

    @DELETE("v1/fundings/{fundingId}/cancel")
    suspend fun cancelFunding(

    ): ApiResponse<Void>

    @PATCH("v1/fundings/{fundingId}/terminate")
    suspend fun terminateFunding(

    ): ApiResponse<Void>

    @POST("v1/fundings/{fundingId}/update")
    suspend fun updateFundingDescription(

    ): ApiResponse<Void>

    @GET("v1/fundings/{fundingId}/join")
    suspend fun joinFunding(
        @Path("fundingGroupId") fundingId: Long
    ): ApiResponse<Void>

    @DELETE("v1/fundings/{fundingId}/resign")
    suspend fun leaveFunding(

    ): ApiResponse<Void>

    @POST("v1/fundings/{fundingId}/deposit")
    suspend fun createDeposit(

    ): ApiResponse<Void>

    @DELETE("v1/fundings/{fundingId}/withdraw/{fundingSupportId}")
    suspend fun withdrawDeposit(

    ): ApiResponse<Void>

    @POST("v1/fundings/{fundingId}/boards/create")
    suspend fun createPost(

    ): ApiResponse<Void>

    // TODO: ApiResponse data - Post DTO로 바꾸기
    @GET("v1/fundings/{fundingId}/boards")
    suspend fun getPost(

    ): ApiResponse<Void>

    @PUT("v1/fundings/{fundingId}/boards/{boardId}")
    suspend fun updatePost(

    ): ApiResponse<Void>

    @DELETE("v1/fundings/{fundingId}/boareds/{boardId}/delete")
    suspend fun deletePost(

    ): ApiResponse<Void>

    @GET("v1/star/favorite")
    suspend fun getMyStars(
    ): ApiResponse<List<MyStarResponse>>
}