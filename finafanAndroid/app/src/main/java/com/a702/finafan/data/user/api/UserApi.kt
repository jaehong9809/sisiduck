package com.a702.finafan.data.user.api

import com.a702.finafan.common.data.dto.ApiResponse
import com.a702.finafan.data.user.model.UserInfoResponse
import retrofit2.http.GET

interface UserApi {
    @GET("v1/user/info")
    suspend fun getUserInfo(): ApiResponse<UserInfoResponse>
}