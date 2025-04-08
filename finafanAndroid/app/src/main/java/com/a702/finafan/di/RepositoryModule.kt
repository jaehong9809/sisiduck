package com.a702.finafan.di

import com.a702.finafan.data.auth.repository.AuthRepositoryImpl
import com.a702.finafan.data.ble.repository.BleScanRepositoryImpl
import com.a702.finafan.data.chatbot.repository.ChatRepositoryImpl
import com.a702.finafan.data.funding.repository.FundingRepositoryImpl
import com.a702.finafan.data.main.repository.MainRepositoryImpl
import com.a702.finafan.data.savings.repository.SavingRepositoryImpl
import com.a702.finafan.data.user.repository.UserRepositoryImpl
import com.a702.finafan.domain.auth.repository.AuthRepository
import com.a702.finafan.domain.ble.repository.BleScanRepository
import com.a702.finafan.domain.chatbot.repository.ChatRepository
import com.a702.finafan.domain.funding.repository.FundingRepository
import com.a702.finafan.domain.main.repository.MainRepository
import com.a702.finafan.domain.savings.repository.SavingRepository
import com.a702.finafan.domain.user.repository.UserRepository
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
    abstract fun bindMainRepository(
        mainRepositoryImpl: MainRepositoryImpl
    ): MainRepository

    @Binds
    @Singleton
    abstract fun bindChatRepository(
        chatRepositoryImpl: ChatRepositoryImpl
    ): ChatRepository

    @Binds
    @Singleton
    abstract fun bindBleScanRepository(
        bleScanRepositoryImpl: BleScanRepositoryImpl
    ): BleScanRepository

    @Binds
    @Singleton
    abstract fun bindSavingRepository(
        starRepositoryImpl: SavingRepositoryImpl
    ): SavingRepository

    @Binds
    @Singleton
    abstract fun bindFundingRepository(
        fundingRepositoryImpl: FundingRepositoryImpl
    ): FundingRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ) : AuthRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ) : UserRepository
}
