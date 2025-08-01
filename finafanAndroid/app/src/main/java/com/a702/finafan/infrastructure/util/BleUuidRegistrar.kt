package com.a702.finafan.infrastructure.util

import android.content.Context
import android.util.Log
import com.a702.finafan.domain.ble.usecase.RegisterBleUuidUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BleUuidRegistrar @Inject constructor(
    @ApplicationContext private val context: Context,
    private val registerBleUuidUseCase: RegisterBleUuidUseCase
) {
    fun registerOnce() {
        val uuid = BleUuidUtil.getRotatingUuid(context)

        CoroutineScope(Dispatchers.IO).launch {
            kotlin.runCatching {
                registerBleUuidUseCase(uuid.toString())
                Log.d("BLE", "✅ UUID 등록 요청: $uuid")
            }.onFailure {
                Log.e("BLE", "❌ UUID 등록 실패: ${it.message}")
            }
        }
    }
}