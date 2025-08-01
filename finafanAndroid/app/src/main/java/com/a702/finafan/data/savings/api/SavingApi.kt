package com.a702.finafan.data.savings.api

import com.a702.finafan.common.data.dto.ApiResponse
import com.a702.finafan.data.savings.dto.request.SavingCreateRequest
import com.a702.finafan.data.savings.dto.response.RankingDetailResponse
import com.a702.finafan.data.savings.dto.response.SavingAccountInfoResponse
import com.a702.finafan.data.savings.dto.response.SavingAccountResponse
import com.a702.finafan.data.savings.dto.response.SavingCreateResponse
import com.a702.finafan.data.savings.dto.response.SavingDepositResponse
import com.a702.finafan.data.savings.dto.response.SavingRankingResponse
import com.a702.finafan.data.savings.dto.response.StarResponse
import com.a702.finafan.data.savings.dto.response.TransactionInfo
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface SavingApi {
    // 스타 목록
    @GET("v1/star")
    suspend fun getStars(): ApiResponse<List<StarResponse>>

    // 스타 검색
    @GET("v1/star/search")
    suspend fun starSearch(@Query(value = "keyword") keyword: String?): ApiResponse<List<StarResponse>>

    // 입금하기
    @Multipart
    @POST("v1/star/deposit")
    suspend fun deposit(
        @Part("request") requestBody: RequestBody,
        @Part imageFile: MultipartBody.Part?
    ): ApiResponse<SavingDepositResponse>

    // 적금 계좌 개설
    @POST("v1/star/savings")
    suspend fun createSaving(@Body request: SavingCreateRequest): ApiResponse<SavingCreateResponse>

    // 적금 계좌 정보 조회
    @GET("v1/star/account/{savingAccountId}")
    suspend fun accountInfo(@Path("savingAccountId") savingAccountId: Long): ApiResponse<SavingAccountResponse>

    // 적금 입금내역 조회
    @GET("v1/star/transaction-histories/{savingAccountId}")
    suspend fun history(@Path("savingAccountId") savingAccountId: Long): ApiResponse<TransactionInfo>

    // 적금 계좌 목록 조회
    @GET("v1/star/accounts")
    suspend fun savingAccounts(): ApiResponse<SavingAccountInfoResponse>

    // 적금 계좌 이름 변경
    @POST("v1/star/alias/{savingAccountId}")
    suspend fun updateSavingName(
        @Path("savingAccountId") savingAccountId: Long,
        @Body request: Map<String, String>
    ): ApiResponse<SavingAccountResponse>

    // 적금 계좌 해지하기
    @DELETE("v1/star/{savingAccountId}")
    suspend fun deleteSavingAccount(@Path("savingAccountId") savingAccountId: Long): ApiResponse<Unit>

    // 적금 일간 랭킹 조회
    @GET("v1/ranking/daily/all-entertainers")
    suspend fun dailyStarRanking(): ApiResponse<List<SavingRankingResponse>>

    // 적금 주간 랭킹 조회
    @GET("v1/ranking/weekly/all-entertainers")
    suspend fun weeklyStarRanking(): ApiResponse<List<SavingRankingResponse>>

    // 적금 누적 랭킹 조회
    @GET("v1/ranking/total/entertainers")
    suspend fun totalStarRanking(): ApiResponse<List<SavingRankingResponse>>

    // 스타별 적금 내역 조회
    @GET("v1/star/{entertainerId}/top-transactions")
    suspend fun starSavingHistory(
        @Path("entertainerId") starId: Long,
        @Query("period") type: String
    ): ApiResponse<RankingDetailResponse>

}