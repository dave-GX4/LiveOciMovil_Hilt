package com.updavid.liveoci_hilt.features.home.repository

import com.updavid.liveoci_hilt.features.home.data.datasource.remote.api.HomeLiveOciApi
import com.updavid.liveoci_hilt.features.home.data.datasource.remote.mapper.toDomain
import com.updavid.liveoci_hilt.features.home.domain.entity.HomeNotification
import com.updavid.liveoci_hilt.features.home.domain.repository.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val api: HomeLiveOciApi
) : HomeRepository {

    override suspend fun getNotifications(
        userId: String,
        limit: Int
    ): List<HomeNotification> {
        val response = api.getNotifications(userId, limit)

        if (!response.success) {
            throw Exception("No se pudieron obtener las notificaciones")
        }

        return response.data.map { it.toDomain() }
    }

    override suspend fun markNotificationRead(notificationId: String) {
        val response = api.markNotificationRead(notificationId)

        if (!response.success) {
            throw Exception(response.error ?: response.message ?: "No se pudo marcar la notificación")
        }
    }

    override suspend fun markAllNotificationsRead(userId: String) {
        val response = api.markAllNotificationsRead(userId)

        if (!response.success) {
            throw Exception(response.error ?: response.message ?: "No se pudieron marcar las notificaciones")
        }
    }
}