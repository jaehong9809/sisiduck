package com.a702.finafan.di.entrypoint

import com.a702.finafan.infrastructure.util.BleUuidRegistrar
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface BleRegistrarEntryPoint {
    fun bleUuidRegistrar(): BleUuidRegistrar
}