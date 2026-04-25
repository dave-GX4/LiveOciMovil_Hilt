package com.updavid.liveoci_hilt.core.sse

import com.updavid.liveoci_hilt.core.sse.dtos.CodeSSEDto
import kotlinx.coroutines.flow.Flow

interface SseDataSource {
    fun streamCode(id: String): Flow<CodeSSEDto>
}