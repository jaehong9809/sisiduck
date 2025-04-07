package com.a702.finafan.data.user.repository

import com.a702.finafan.data.user.api.UserApi
import com.a702.finafan.domain.user.model.User
import com.a702.finafan.domain.user.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService : UserApi
) : UserRepository {
    override suspend fun getUserInfo(): User {
        return apiService.getUserInfo().toDomain()
    }
}