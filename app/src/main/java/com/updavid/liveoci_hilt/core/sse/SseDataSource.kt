package com.updavid.liveoci_hilt.core.sse

import com.updavid.liveoci_hilt.core.sse.dtos.CodeUpdateEventDto
import com.updavid.liveoci_hilt.core.sse.dtos.FriendListUpdateEventDto
import com.updavid.liveoci_hilt.features.home.data.datasource.remote.mapper.NotificationResponseDto
import com.updavid.liveoci_hilt.features.home.domain.entity.Notification
import kotlinx.coroutines.flow.Flow

interface SseDataSource {
    fun streamCode(id: String): Flow<CodeUpdateEventDto>
    fun streamFriendUpdates(id: String): Flow<FriendListUpdateEventDto>
    fun streamNotifications(id: String): Flow<NotificationResponseDto>
}