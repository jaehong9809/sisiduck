package com.a702.finafan.infrastructure.util

import android.Manifest
import android.app.Activity
import android.os.Build
import androidx.core.app.ActivityCompat

object PermissionHelper {
    fun requestAllPermissions(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(
                    Manifest.permission.BLUETOOTH_ADVERTISE,
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_CONNECT,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                1001
            )
        }
    }
}