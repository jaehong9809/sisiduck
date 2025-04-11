package com.a702.finafan.infrastructure.util

import android.content.Context
import java.util.UUID

object BleUuidUtil {

    private const val PREF_NAME = "ble_prefs"
    private const val KEY_UUID = "advertising_uuid"
    private const val KEY_TIMESTAMP = "uuid_timestamp"
    private const val TTL_MILLIS = 5 * 60 * 1000L

    fun getRotatingUuid(context: Context): UUID {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val lastUuid = prefs.getString(KEY_UUID, null)
        val lastTime = prefs.getLong(KEY_TIMESTAMP, 0L)
        val now = System.currentTimeMillis()

        return if (lastUuid != null && now - lastTime < TTL_MILLIS) {
            UUID.fromString(lastUuid)
        } else {
            val newUuid = UUID.randomUUID()
            prefs.edit()
                .putString(KEY_UUID, newUuid.toString())
                .putLong(KEY_TIMESTAMP, now)
                .apply()
            newUuid
        }
    }
}
