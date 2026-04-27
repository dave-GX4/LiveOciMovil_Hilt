package com.updavid.liveoci_hilt.features.home.domain.entity

data class Notification(
    val id: String,
    val userId: String,
    val type: String,
    val title: String,
    val body: String,
    val data: NotificationData?,
    val isRead: Boolean,
    val channel: String,
)