package com.a702.finafan.data.savings.api

import com.a702.finafan.common.data.dto.ApiResponse
import com.a702.finafan.data.savings.dto.request.SavingCreateRequest
import com.a702.finafan.data.savings.dto.request.SavingDepositRequest
import com.a702.finafan.data.savings.dto.response.AccountResponse
import com.a702.finafan.data.savings.dto.response.SavingAccountResponse
import com.a702.finafan.data.savings.dto.response.SavingCreateResponse
import com.a702.finafan.data.savings.dto.response.StarResponse
import com.a702.finafan.data.savings.dto.response.TransactionInfo
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SavingApi {
    // 스타 목록
    @GET("v1/star")
    suspend fun getStars(): ApiResponse<List<StarResponse>>

    // 스타 목록
    @GET("v1/star/search")
    suspend fun starSearch(): ApiResponse<List<StarResponse>>

    // 입금하기
    @POST("v1/star/deposit")
    suspend fun deposit(@Body request: SavingDepositRequest): ApiResponse<String>

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
    suspend fun accountList(): ApiResponse<List<SavingAccountResponse>>

    // 출금 계좌 목록 조회
    @GET("v1/star/withdrawal-accounts")
    suspend fun withdrawAccount(): ApiResponse<List<AccountResponse>>
}