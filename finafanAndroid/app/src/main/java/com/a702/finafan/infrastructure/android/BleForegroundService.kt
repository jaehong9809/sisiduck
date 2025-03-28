package com.a702.finafan.infrastructure.android

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.a702.finafan.di.entrypoint.BleRepositoryEntryPoint
import com.a702.finafan.infrastructure.ble.BleAdvertiser
import com.a702.finafan.infrastructure.ble.BleScanner
import com.a702.finafan.infrastructure.util.BleUuidUtil
import com.a702.finafan.infrastructure.util.NotificationFactory
import dagger.hilt.android.EntryPointAccessors


class BleForegroundService : Service() {
    private lateinit var advertiser: BleAdvertiser
    private lateinit var scanner: BleScanner

    override fun onCreate() {
        super.onCreate()
        advertiser = BleAdvertiser(this)

        val repo = EntryPointAccessors.fromApplication(
            applicationContext,
            BleRepositoryEntryPoint::class.java
        ).bleScanRepository()

        scanner = BleScanner(this) { scannedUuid ->
            repo.addScannedUuid(scannedUuid)
        }

        val uuid = BleUuidUtil.getRotatingUuid(this)
        advertiser.start(uuid,
            onStart = { Log.d("BLE_SERVICE", "Advertise started") },
            onFailure = { Log.e("BLE_SERVICE", "Advertise failed: $it") }
        )
        scanner.start()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(1, NotificationFactory.create(applicationContext))
        return START_STICKY
    }

    override fun onDestroy() {
        advertiser.stop()
        scanner.stop()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
