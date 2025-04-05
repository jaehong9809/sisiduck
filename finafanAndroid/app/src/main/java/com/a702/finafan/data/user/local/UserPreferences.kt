package com.a702.finafan.data.user.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferences @Inject constructor(@ApplicationContext context: Context) {
    private val Context.dataStore by preferencesDataStore("user_prefs")
    private val dataStore = context.dataStore

    companion object {
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    }

    val userStateFlow: Flow<Boolean> = dataStore.data
        .map { prefs -> prefs[IS_LOGGED_IN] ?: false }

    suspend fun setLoginState(isLoggedIn: Boolean) {
        dataStore.edit { prefs ->
            prefs[IS_LOGGED_IN] = isLoggedIn
        }
    }
}