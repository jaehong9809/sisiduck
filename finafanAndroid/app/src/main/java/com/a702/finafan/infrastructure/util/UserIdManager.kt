package com.a702.finafan.infrastructure.util

import android.content.Context
import java.util.UUID

object UserIdManager {

    private const val PREF_USER_ID = "user_id"

    fun getOrCreateUserId(context: Context): String {
        val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        var userId = prefs.getString(PREF_USER_ID, null)
        if (userId == null) {
            userId = UUID.randomUUID().toString()
            prefs.edit().putString(PREF_USER_ID, userId).apply()
        }
        return userId
    }
}