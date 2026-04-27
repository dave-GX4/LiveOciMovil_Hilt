package com.updavid.liveoci_hilt.core.datastore

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class DataStoreManagerImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
): DataStoreService {
    private val USER_ID_KEY = stringPreferencesKey("user_id")
    private val USER_PHOTO_URL_KEY = stringPreferencesKey("user_photo_url")

    override suspend fun saveUserId(id: String) {
        dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = id
        }
    }

    override fun getUserId(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[USER_ID_KEY]
        }
    }

    override suspend fun saveUserPhotoUrl(url: String) {
        dataStore.edit { it[USER_PHOTO_URL_KEY] = url }
    }

    override fun getUserPhotoUrl(): Flow<String?> {
        return dataStore.data
            .map { preferences -> preferences[USER_PHOTO_URL_KEY] }
            .distinctUntilChanged()
    }

    override suspend fun clearSession() {
        Log.d("AppDebug", "DataStore: Limpiando toda la sesión...")
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}