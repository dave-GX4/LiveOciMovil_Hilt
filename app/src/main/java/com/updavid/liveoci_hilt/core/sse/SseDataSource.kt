package com.updavid.liveoci_hilt.core.sse

import com.updavid.liveoci_hilt.core.sse.dtos.CodeUpdateEventDto
import com.updavid.liveoci_hilt.core.sse.dtos.FriendListUpdateEventDto
import kotlinx.coroutines.flow.Flow

interface SseDataSource {
    fun streamCode(id: String): Flow<CodeUpdateEventDto>
    fun streamFriendUpdates(id: String): Flow<FriendListUpdateEventDto>
}