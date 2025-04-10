package com.a702.finafan

import android.app.Application
import com.a702.finafan.infrastructure.util.BleUuidRegistrar
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class FinafanApp : Application() {

    @Inject
    lateinit var bleUuidRegistrar: BleUuidRegistrar

    // 앱 작동시 BleUuid 서버에 자동 등록
    override fun onCreate() {
        super.onCreate()
        bleUuidRegistrar.registerOnce()
    }
}