package com.a702.finafan.di

import com.a702.finafan.data.chatbot.api.ChatApi
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
}
