package com.updavid.liveoci_hilt.core.datastore

import kotlinx.coroutines.flow.Flow

interface DataStoreService {
    suspend fun saveUserId(id: String)
    fun getUserId(): Flow<String?>
    suspend fun clearSession()
}