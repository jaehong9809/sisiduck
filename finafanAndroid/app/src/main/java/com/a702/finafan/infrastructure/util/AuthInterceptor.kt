package com.a702.finafan.infrastructure.util

import android.content.Context
import android.util.Log
import com.a702.finafan.data.auth.TokenDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val context: Context
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        runBlocking {
            val token = TokenDataStore.getAccessToken(context).first()
            Log.d("AuthInterceptor", "AccessToken: $token")
            requestBuilder.addHeader("Authorization", "Bearer ${token?.trim()}")
        }

        return chain.proceed(requestBuilder.build())
    }
}
