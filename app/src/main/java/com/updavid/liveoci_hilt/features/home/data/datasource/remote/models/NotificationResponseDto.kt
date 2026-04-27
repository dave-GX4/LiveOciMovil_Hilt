package com.updavid.liveoci_hilt.features.home.data.datasource.remote.mapper

import com.updavid.liveoci_hilt.features.home.data.datasource.remote.models.NotificationDataDto

data class NotificationResponseDto(
    val id: String,
    val userId: String,
    val type: String,
    val title: String,
    val body: String,
    val data: NotificationDataDto?,
    val isRead: Boolean,
    val channel: String
)