package com.updavid.liveoci_hilt.features.home.domain.usecase

import com.updavid.liveoci_hilt.features.home.domain.repository.HomeRepository
import javax.inject.Inject

class MarkAllHomeNotificationsReadUseCase @Inject constructor(
    private val repository: HomeRepository
) {
    suspend operator fun invoke(userId: String) {
        repository.markAllNotificationsRead(userId)
    }
}