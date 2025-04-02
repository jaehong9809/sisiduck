package com.a702.finafan.data.auth.repository

import android.content.Context
import android.util.Log
import com.a702.finafan.common.domain.DataResource
import com.a702.finafan.data.auth.TokenDataStore
import com.a702.finafan.domain.auth.repository.AuthRepository
import com.a702.finafan.domain.user.model.User
import kotlinx.coroutines.flow.Flow

class AuthRepositoryImpl(
    private val context: Context
) : AuthRepository {

    override fun login(token: String): Flow<DataResource<User>> {
        TODO("Not yet implemented")
    }

    override fun logout(): Flow<DataResource<Unit>> {
        TODO("Not yet implemented")
    }

    override fun getLoginState(): Flow<DataResource<Boolean>> {
        TODO("Not yet implemented")
    }

    override suspend fun saveAccessToken(token: String) {
        TokenDataStore.setAccessToken(context, token)
        Log.d("Saved Access Token", token)
    }

    //TO DO: 추후, 서버에 토큰 검증 요청 or ReIssue 요청

    override fun observeAccessToken(): Flow<String?> {
        return TokenDataStore.getAccessToken(context)
    }
}