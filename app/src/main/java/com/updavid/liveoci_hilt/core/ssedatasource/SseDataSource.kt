package com.updavid.liveoci_hilt.core.ssedatasource

import com.updavid.liveoci_hilt.core.ssedatasource.dtos.CodeSSEDto
import kotlinx.coroutines.flow.Flow

interface SseDataSource {
    fun streamCode(id: String): Flow<CodeSSEDto>
}