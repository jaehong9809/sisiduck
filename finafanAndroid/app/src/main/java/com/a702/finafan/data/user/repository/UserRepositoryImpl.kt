package com.a702.finafan.data.user.repository

import com.a702.finafan.common.domain.DataResource
import com.a702.finafan.common.utils.safeApiCall
import com.a702.finafan.data.user.api.UserApi
import com.a702.finafan.domain.user.model.User
import com.a702.finafan.domain.user.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApi : UserApi
) : UserRepository {
    override suspend fun getUserInfo(): DataResource<User> {
        return safeApiCall { userApi.getUserInfo().toDomain() }
    }
}