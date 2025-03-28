package com.a702.finafan.di

import com.a702.finafan.data.ble.repository.BleScanRepositoryImpl
import com.a702.finafan.data.chatbot.repository.ChatRepositoryImpl
import com.a702.finafan.domain.ble.repository.BleScanRepository
import com.a702.finafan.data.savings.repository.SavingRepositoryImpl
import com.a702.finafan.domain.chatbot.repository.ChatRepository
import com.a702.finafan.domain.savings.repository.SavingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindChatRepository(
        chatRepositoryImpl: ChatRepositoryImpl
    ): ChatRepository

    @Binds
    @Singleton
    abstract fun bindBleScanRepository(
        impl: BleScanRepositoryImpl
    ): BleScanRepository

    @Binds
    @Singleton
    abstract fun bindSavingRepository(
        starRepositoryImpl: SavingRepositoryImpl
    ): SavingRepository

}
