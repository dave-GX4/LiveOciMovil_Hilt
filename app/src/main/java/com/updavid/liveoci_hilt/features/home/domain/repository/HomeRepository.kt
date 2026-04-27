package com.updavid.liveoci_hilt.features.home.domain.repository

import com.updavid.liveoci_hilt.features.home.domain.entity.HomeNotification


interface HomeRepository {

    suspend fun getNotifications(
        userId: String,
        limit: Int = 20
    ): List<HomeNotification>

    suspend fun markNotificationRead(notificationId: String)

    suspend fun markAllNotificationsRead(userId: String)
}