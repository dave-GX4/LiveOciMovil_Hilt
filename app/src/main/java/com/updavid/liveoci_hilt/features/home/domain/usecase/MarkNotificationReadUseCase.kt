package com.updavid.liveoci_hilt.features.home.domain.usecase

import com.updavid.liveoci_hilt.features.home.domain.entity.NotificationMessage
import com.updavid.liveoci_hilt.features.home.domain.repository.NotificationRepository
import javax.inject.Inject

class MarkNotificationReadUseCase @Inject constructor(
    private val repository: NotificationRepository
) {
    suspend operator fun invoke(notificationId: String): Result<NotificationMessage> {
        return runCatching { repository.markNotificationRead(notificationId) }
    }
}