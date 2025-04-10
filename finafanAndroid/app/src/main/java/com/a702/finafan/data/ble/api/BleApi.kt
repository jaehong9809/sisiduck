package com.a702.finafan.data.ble.api

import com.a702.finafan.common.data.dto.ApiResponse
import com.a702.finafan.data.ble.dto.request.MatchFansUuidRequest
import com.a702.finafan.data.ble.dto.request.RegisterBleUuidRequest
import com.a702.finafan.data.ble.dto.response.FanDepositResponse
import com.a702.finafan.data.ble.dto.response.MatchFansResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface BleApi {

    @POST("v1/ble/uuid")
    suspend fun registerUuid(
        @Body request: RegisterBleUuidRequest
    ): ApiResponse<Unit>

    @POST("v1/ble/match-fans")
    suspend fun matchFans(
        @Body request: MatchFansUuidRequest
    ): ApiResponse<List<MatchFansResponse>>

    @GET("v1/ble/matched-fan-deposits")
    suspend fun getMatchedFanDeposits(): ApiResponse<List<FanDepositResponse>>
}