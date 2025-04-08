package com.a702.finafan.data.account.api

import com.a702.finafan.common.data.dto.ApiResponse
import com.a702.finafan.data.account.dto.response.AccountResponse
import com.a702.finafan.data.account.dto.response.BankResponse
import com.a702.finafan.data.savings.dto.request.AccountNosRequest
import com.a702.finafan.data.savings.dto.request.BankIdsRequest
import com.a702.finafan.domain.savings.model.Transaction
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AccountApi {

    // 출금 계좌 목록 조회
    @GET("v1/star/withdrawal-accounts")
    suspend fun withdrawAccount(): ApiResponse<List<AccountResponse>>

    // 연결 계좌 삭제
    @DELETE("v1/star/{depositAccountId}/withdrawal-connection")
    suspend fun deleteConnectAccount(@Path("depositAccountId") depositAccountId: Long): ApiResponse<Unit>

    // 은행 목록 조회
    @GET("v1/code/bank")
    suspend fun bankList(): ApiResponse<List<BankResponse>>

    // 은행 선택 -> 연결 가능 계좌 목록 조회
    @POST("v1/bank/accounts")
    suspend fun selectBanks(@Body request: BankIdsRequest): ApiResponse<List<AccountResponse>>

    // 연결 계좌 선택
    @POST("v1/bank/accounts/connect")
    suspend fun selectAccounts(@Body request: AccountNosRequest): ApiResponse<List<AccountResponse>>

    // 1원 인증 코드 전송
    @POST("v1/account-auth/certification")
    suspend fun certificateAccount(): ApiResponse<Unit>

    // 1원 인증 코드 확인
    @POST("v1/account-auth/verification")
    suspend fun verifyAccount(): ApiResponse<Unit>

    // 연결 계좌 입금 목록 확인
    @GET("v1/demand-deposit/transaction-histories")
    suspend fun connectTransactionHistory(): ApiResponse<List<Transaction>>

}