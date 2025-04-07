package com.a702.finafan.di

import com.a702.finafan.data.chatbot.api.ChatApi
import com.a702.finafan.data.funding.api.FundingApi
import com.a702.finafan.data.main.api.MainApi
import com.a702.finafan.data.savings.api.SavingApi
import com.a702.finafan.data.user.api.UserApi
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
    fun provideMainApi(@NetworkModule.MainRetrofit retrofit: Retrofit)
    : MainApi {
        return retrofit.create(MainApi::class.java)
    }

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

    @Provides
    @Singleton
    fun provideUserApi(@NetworkModule.MainRetrofit retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }
}
