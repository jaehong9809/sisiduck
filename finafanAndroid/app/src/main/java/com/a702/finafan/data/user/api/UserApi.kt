package com.a702.finafan.data.user.api

import com.a702.finafan.data.user.model.UserInfoResponse

interface UserApi {
    suspend fun getUserInfo(): UserInfoResponse
}