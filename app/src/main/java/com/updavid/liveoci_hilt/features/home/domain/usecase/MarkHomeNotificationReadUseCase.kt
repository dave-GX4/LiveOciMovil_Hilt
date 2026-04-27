package com.updavid.liveoci_hilt.features.home.domain.usecase

import com.updavid.liveoci_hilt.features.home.domain.repository.HomeRepository
import javax.inject.Inject

class MarkHomeNotificationReadUseCase @Inject constructor(
    private val repository: HomeRepository
) {
    suspend operator fun invoke(notificationId: String) {
        repository.markNotificationRead(notificationId)
    }
}