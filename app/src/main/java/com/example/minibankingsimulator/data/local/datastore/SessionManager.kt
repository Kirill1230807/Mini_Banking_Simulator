package com.example.minibankingsimulator.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class SessionManager(
    private val datastore: DataStore<Preferences>
) {
    companion object {
        val USER_ID = stringPreferencesKey("user_id")
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    }

    val userId: Flow<String?> = datastore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences()) else throw exception
        }
        .map { preferences ->
            preferences[USER_ID]
        }

    val isLoggedIn : Flow<Boolean> = datastore.data
        .map { preferences ->
            preferences[IS_LOGGED_IN] ?: false
        }

    suspend fun saveSession(userId: String) {
        datastore.edit { preferences ->
            preferences[USER_ID] = userId
            preferences[IS_LOGGED_IN] = true
        }
    }

    suspend fun clearSession() {
        datastore.edit { preferences ->
            preferences.clear()
        }
    }
}