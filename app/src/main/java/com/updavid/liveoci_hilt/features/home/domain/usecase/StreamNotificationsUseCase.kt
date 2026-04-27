package com.updavid.liveoci_hilt.features.home.domain.usecase

import com.updavid.liveoci_hilt.features.home.domain.entity.Notification
import com.updavid.liveoci_hilt.features.home.domain.repository.NotificationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StreamNotificationsUseCase @Inject constructor(
    private val repository: NotificationRepository
) {
    operator fun invoke(): Flow<Notification> {
        return repository.streamNotifications()
    }
}