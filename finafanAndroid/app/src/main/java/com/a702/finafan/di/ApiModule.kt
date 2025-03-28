package com.a702.finafan.di

import com.a702.finafan.data.chatbot.api.ChatApi
import com.a702.finafan.data.funding.api.FundingApi
import com.a702.finafan.data.savings.api.SavingApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideChatApi(@NetworkModule.AiChatRetrofit retrofit: Retrofit)
    : ChatApi {
        return retrofit.create(ChatApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSavingApi(@NetworkModule.MainRetrofit retrofit: Retrofit)
    : SavingApi {
        return retrofit.create(SavingApi::class.java)
    }

    @Provides
    @Singleton
    fun provideFundingApi(@NetworkModule.MainRetrofit retrofit: Retrofit): FundingApi {
        return retrofit.create(FundingApi::class.java)
    }
}
