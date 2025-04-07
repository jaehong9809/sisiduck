package com.a702.finafan.domain.user.repository

import com.a702.finafan.domain.user.model.User

interface UserRepository {
    suspend fun getUserInfo(): User
}