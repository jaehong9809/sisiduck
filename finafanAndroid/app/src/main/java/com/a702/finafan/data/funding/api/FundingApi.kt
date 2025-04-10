package com.a702.finafan.data.funding.api

import com.a702.finafan.common.data.dto.ApiResponse
import com.a702.finafan.data.funding.dto.request.CancelFundingRequest
import com.a702.finafan.data.funding.dto.request.DepositRequest
import com.a702.finafan.data.funding.dto.request.FormRequest
import com.a702.finafan.data.funding.dto.request.StartFundingRequest
import com.a702.finafan.data.funding.dto.request.UpdateFundingDescriptionRequest
import com.a702.finafan.data.funding.dto.request.WithdrawDepositRequest
import com.a702.finafan.data.funding.dto.response.DepositResponse
import com.a702.finafan.data.funding.dto.response.FormResponse
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
        @Path("fundingId") fundingId: Long,
        @Body request: CancelFundingRequest
    ): ApiResponse<Void>

    @PATCH("v1/fundings/{fundingId}/terminate")
    suspend fun terminateFunding(
        @Path("fundingId") fundingId: Long
    ): ApiResponse<Void>

    @POST("v1/fundings/{fundingId}/update")
    suspend fun updateFundingDescription(
        @Path("fundingId") fundingId: Long,
        @Body request: UpdateFundingDescriptionRequest
    ): ApiResponse<Void>

    @GET("v1/fundings/{fundingId}/join")
    suspend fun joinFunding(
        @Path("fundingId") fundingId: Long
    ): ApiResponse<Void>

    @DELETE("v1/fundings/{fundingId}/resign")
    suspend fun leaveFunding(
        @Path("fundingId") fundingId: Long
    ): ApiResponse<Void>

    @POST("v1/fundings/{fundingId}/deposit")
    suspend fun createDeposit(
        @Path("fundingId") fundingId: Long,
        @Body request: DepositRequest
    ): ApiResponse<Void>

    @DELETE("v1/fundings/{fundingId}/withdraw")
    suspend fun withdrawDeposit(
        @Path("fundingId") fundingId: Long,
        @Body request: WithdrawDepositRequest
    ): ApiResponse<Void>

    @POST("v1/fundings/{fundingId}/board/create")
    suspend fun createForm(
        @Path("fundingId") fundingId: Long,
        @Body request: FormRequest
    ): ApiResponse<Void>

    @GET("v1/fundings/{fundingId}/board")
    suspend fun getForm(
        @Path("fundingId") fundingId: Long
    ): ApiResponse<List<FormResponse>>

    @PUT("v1/fundings/{fundingId}/board/update}")
    suspend fun updateForm(
        @Path("fundingId") fundingId: Long
    ): ApiResponse<Void>

    @DELETE("v1/fundings/{fundingId}/board/delete")
    suspend fun deleteForm(
        @Path("fundingId") fundingId: Long
    ): ApiResponse<Void>

    @GET("v1/star/favorite")
    suspend fun getMyStars(
    ): ApiResponse<List<MyStarResponse>>
}