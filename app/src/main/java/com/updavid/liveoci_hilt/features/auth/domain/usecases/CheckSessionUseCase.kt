package com.updavid.liveoci_hilt.features.auth.domain.usecases

import com.updavid.liveoci_hilt.core.datastore.DataStoreService
import jakarta.inject.Inject
import kotlinx.coroutines.flow.first

class CheckSessionUseCase @Inject constructor(
    private val dataStore: DataStoreService
) {
    suspend operator fun invoke(): Boolean {
        val id = dataStore.getUserId().first()
        return !id.isNullOrBlank()
    }
}