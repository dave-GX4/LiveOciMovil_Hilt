package com.updavid.liveoci_hilt.features.auth.domain.usecases

import android.util.Log
import com.updavid.liveoci_hilt.core.datastore.DataStoreService
import jakarta.inject.Inject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull

class CheckSessionUseCase @Inject constructor(
    private val dataStore: DataStoreService
) {
    suspend operator fun invoke(): Boolean {
        return try {
            val id = dataStore.getUserId().firstOrNull()

            Log.d("AUTH_TEST", "El ID recuperado de DataStore es: '$id'")

            !id.isNullOrBlank() && id != "null"
        } catch (e: Exception) {
            Log.e("AUTH_TEST", "Error leyendo DataStore", e)
            false
        }
    }
}