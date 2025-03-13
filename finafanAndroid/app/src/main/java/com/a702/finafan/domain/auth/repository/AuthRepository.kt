package com.a702.finafan.domain.auth.repository

import com.a702.finafan.common.domain.DataResource
import com.a702.finafan.domain.auth.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun login(token: String): Flow<DataResource<User>>
    fun logout() : Flow<DataResource<Unit>>

    fun getLoginState(): Flow<DataResource<Boolean>>
}