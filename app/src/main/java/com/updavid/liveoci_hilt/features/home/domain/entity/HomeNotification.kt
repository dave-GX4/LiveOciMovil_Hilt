package com.updavid.liveoci_hilt.features.home.domain.entity

data class HomeNotification(
    val id: String,
    val userId: String,
    val type: String,
    val title: String,
    val body: String,
    val data: Map<String, Any?> = emptyMap(),
    val isRead: Boolean,
    val channel: String,
    val createdAt: String?,
    val source: HomeNotificationSource = HomeNotificationSource.HTTP
)

enum class HomeNotificationSource {
    HTTP,
    SSE
}