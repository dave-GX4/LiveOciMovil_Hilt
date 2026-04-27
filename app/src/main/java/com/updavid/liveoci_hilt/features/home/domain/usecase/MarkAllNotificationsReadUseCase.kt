package com.updavid.liveoci_hilt.features.home.domain.usecase

import com.updavid.liveoci_hilt.features.home.domain.entity.NotificationMessage
import com.updavid.liveoci_hilt.features.home.domain.repository.NotificationRepository
import javax.inject.Inject

class MarkAllNotificationsReadUseCase @Inject constructor(
    private val repository: NotificationRepository
) {
    suspend operator fun invoke(): Result<NotificationMessage> {
        return runCatching { repository.markAllNotificationsRead() }
    }
}