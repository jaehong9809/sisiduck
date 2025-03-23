package com.a702.finafan.di

import com.a702.finafan.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    annotation class AiChatRetrofit

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer ") // 추후 토큰 추가
                    .build()
                chain.proceed(request)
            }
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

    @AiChatRetrofit
    @Provides
    @Singleton
    fun provideAiChatRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL) // 추후 서버 분리되면 baseUrl 변경
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
    }
}