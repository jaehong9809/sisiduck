package com.a702.finafan.data.auth

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.a702.finafan.data.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object TokenDataStore {

    private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")

    suspend fun setAccessToken(context: Context, token: String) {
        context.dataStore.edit { prefs ->
            prefs[ACCESS_TOKEN_KEY] = token
        }
    }

    fun getAccessToken(context: Context): Flow<String?> {
        return context.dataStore.data.map { prefs ->
            prefs[ACCESS_TOKEN_KEY]
        }
    }
}