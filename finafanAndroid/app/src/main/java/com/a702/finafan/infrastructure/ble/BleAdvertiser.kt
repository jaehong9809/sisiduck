package com.a702.finafan.infrastructure.ble


import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.AdvertiseCallback
import android.bluetooth.le.AdvertiseData
import android.bluetooth.le.AdvertiseSettings
import android.content.Context
import android.os.ParcelUuid
import android.util.Log
import com.a702.finafan.infrastructure.util.BlePermissionChecker
import java.util.UUID


class BleAdvertiser(
    private val context: Context
) {
    private val bluetoothAdapter: BluetoothAdapter =
        (context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter

    private val bluetoothLeAdvertiser = bluetoothAdapter.bluetoothLeAdvertiser

    private var advertiseCallback: AdvertiseCallback? = null

    fun start(uuid: UUID, onStart: (() -> Unit)? = null, onFailure: ((Int) -> Unit)? = null) {
        if (!BlePermissionChecker.hasBleAdvertisePermission(context)) {
            Log.w("BLE_ADVERTISER", "Advertise permission not granted")
            onFailure?.invoke(AdvertiseCallback.ADVERTISE_FAILED_INTERNAL_ERROR)
            return
        }

        val advertiser = bluetoothLeAdvertiser
        if (advertiser == null) {
            Log.e("BLE_ADVERTISER", "BLE advertiser is null (device may not support BLE advertise)")
            onFailure?.invoke(AdvertiseCallback.ADVERTISE_FAILED_FEATURE_UNSUPPORTED)
            return
        }

        val settings = AdvertiseSettings.Builder()
            .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY)
            .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_MEDIUM)
            .setConnectable(false)
            .build()

        val data = AdvertiseData.Builder()
            .addServiceUuid(ParcelUuid(uuid))
            .setIncludeDeviceName(false)
            .build()

        Log.d("BLE_ADVERTISER", "📢 Prepared AdvertiseData with UUID: $uuid")

        advertiseCallback = object : AdvertiseCallback() {
            override fun onStartSuccess(settingsInEffect: AdvertiseSettings) {
                onStart?.invoke()
            }

            override fun onStartFailure(errorCode: Int) {
                onFailure?.invoke(errorCode)
            }
        }

        try {
            advertiser.startAdvertising(settings, data, advertiseCallback)
        } catch (e: SecurityException) {
            Log.e("BLE_ADVERTISER", "SecurityException: ${e.message}")
            onFailure?.invoke(AdvertiseCallback.ADVERTISE_FAILED_INTERNAL_ERROR)
        }
    }

    fun stop() {
        val advertiser = bluetoothLeAdvertiser
        try {
            advertiseCallback?.let {
                advertiser?.stopAdvertising(it)
            }
        } catch (e: SecurityException) {
            Log.e("BLE_ADVERTISER", "SecurityException on stopAdvertising: ${e.message}")
        } finally {
            advertiseCallback = null
        }
    }
}