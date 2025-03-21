package com.a702.finafan.di

import android.content.Context
import com.a702.finafan.common.utils.SpeechRecognizerHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SpeechModule {

    @Provides
    @Singleton
    fun provideSpeechRecognizerHelper(@ApplicationContext context: Context): SpeechRecognizerHelper {
        return SpeechRecognizerHelper(context)
    }
}
