package com.updavid.liveoci_hilt.features.home.domain.repository

import com.updavid.liveoci_hilt.features.home.domain.entity.Notification
import com.updavid.liveoci_hilt.features.home.domain.entity.NotificationMessage

interface NotificationRepository {
    suspend fun getNotifications(
        limit: Int = 20
    ): List<Notification>
    suspend fun markAllNotificationsRead(): NotificationMessage
    suspend fun markNotificationRead(notificationId: String): NotificationMessage
}