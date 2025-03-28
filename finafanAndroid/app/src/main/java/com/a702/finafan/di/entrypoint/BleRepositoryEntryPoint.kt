package com.a702.finafan.di.entrypoint

import com.a702.finafan.domain.ble.repository.BleScanRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface BleRepositoryEntryPoint {
    fun bleScanRepository(): BleScanRepository
}