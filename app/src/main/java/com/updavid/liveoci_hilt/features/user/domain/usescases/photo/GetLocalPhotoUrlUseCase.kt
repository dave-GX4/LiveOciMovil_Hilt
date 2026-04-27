package com.updavid.liveoci_hilt.features.user.domain.usescases.photo

import com.updavid.liveoci_hilt.core.datastore.DataStoreService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocalPhotoUrlUseCase @Inject constructor(
    private val dataStore: DataStoreService
) {
    operator fun invoke(): Flow<String?> = dataStore.getUserPhotoUrl()
}