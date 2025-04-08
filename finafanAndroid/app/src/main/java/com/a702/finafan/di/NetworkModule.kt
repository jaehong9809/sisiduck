package com.a702.finafan.di

import android.content.Context
import com.a702.finafan.BuildConfig
import com.a702.finafan.infrastructure.util.AuthInterceptor
import com.a702.finafan.infrastructure.util.UserIdManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class MainRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AiOkHttpClient

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AiChatRetrofit


    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationContext context: Context
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(AuthInterceptor(context))
            .build()
    }

    @Provides
    @Singleton
    fun provideConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @MainRetrofit
    @Provides
    @Singleton
    fun provideMainRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL) // 빌드 시점에 baseUrl 설정
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
    }


    @AiOkHttpClient
    @Provides
    @Singleton
    fun provideAiOkHttpClient(
        @ApplicationContext context: Context
    ): OkHttpClient {
        val userId = UserIdManager.getOrCreateUserId(context)

        val userIdInterceptor = Interceptor { chain ->
            val original = chain.request()
            val requestWithUserId = original.newBuilder()
                .addHeader("X-User-Id", userId)
                .build()
            chain.proceed(requestWithUserId)
        }

        return OkHttpClient.Builder()
            .addInterceptor(userIdInterceptor)
            .readTimeout(0, TimeUnit.MILLISECONDS)
            .build()
    }

    @AiChatRetrofit
    @Provides
    @Singleton
    fun provideAiChatRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.AI_URL)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
    }
}