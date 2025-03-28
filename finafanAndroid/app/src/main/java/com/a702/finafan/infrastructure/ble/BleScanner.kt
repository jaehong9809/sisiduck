package com.a702.finafan.infrastructure.ble

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.util.Log
import com.a702.finafan.infrastructure.util.BlePermissionChecker
import java.util.UUID


class BleScanner(
    private val context: Context,
    private val onScanResult: (UUID) -> Unit
) {
    private val bluetoothAdapter: BluetoothAdapter =
        (context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter

    private val bluetoothLeScanner = bluetoothAdapter.bluetoothLeScanner

    private val scanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            result.scanRecord?.serviceUuids?.forEach { parcelUuid ->
                Log.d("BLE_SCANNER", "🔍 Found UUID: ${parcelUuid.uuid}")
                onScanResult(parcelUuid.uuid)
            }
        }

        override fun onBatchScanResults(results: MutableList<ScanResult>?) {
            results?.forEach { result ->
                result.scanRecord?.serviceUuids?.forEach { parcelUuid ->
                    Log.d("BLE_SCANNER", "📦 Batch UUID: ${parcelUuid.uuid}")
                    onScanResult(parcelUuid.uuid)
                }
            }
        }

        override fun onScanFailed(errorCode: Int) {
            Log.e("BLE_SCANNER", "Scan failed: $errorCode")
        }
    }

    fun start() {
        if (!BlePermissionChecker.hasBleScanPermission(context)) {
            Log.w("BLE_SCANNER", "Scan permission not granted")
            return
        }

        val settings = ScanSettings.Builder()
            .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
            .build()

        try {
            bluetoothLeScanner?.startScan(null, settings, scanCallback)
        } catch (e: SecurityException) {
            Log.e("BLE_SCANNER", "SecurityException on startScan: ${e.message}")
        }
    }

    fun stop() {
        try {
            bluetoothLeScanner?.stopScan(scanCallback)
        } catch (e: SecurityException) {
            Log.e("BLE_SCANNER", "SecurityException on stopScan: ${e.message}")
        }
    }
}
