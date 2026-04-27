package com.updavid.liveoci_hilt.features.home.domain.usecase

import com.updavid.liveoci_hilt.features.home.domain.entity.HomeNotification
import com.updavid.liveoci_hilt.features.home.domain.repository.HomeRepository
import javax.inject.Inject

class GetHomeNotificationsUseCase @Inject constructor(
    private val repository: HomeRepository
) {
    suspend operator fun invoke(
        userId: String,
        limit: Int = 20
    ): List<HomeNotification> {
        return repository.getNotifications(userId, limit)
    }
}