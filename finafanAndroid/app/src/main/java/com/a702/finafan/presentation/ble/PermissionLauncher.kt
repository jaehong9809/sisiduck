package com.a702.finafan.presentation.ble

import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun rememberBlePermissionLauncher(
    onGranted: () -> Unit,
    onDenied: () -> Unit = {}
): ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>> {
    val context = LocalContext.current

    return rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        val allGranted = result.values.all { it }
        if (allGranted) {
            onGranted()
        } else {
            Toast.makeText(context, "필수 권한이 거부되었습니다.", Toast.LENGTH_SHORT).show()
            onDenied()
        }
    }
}
