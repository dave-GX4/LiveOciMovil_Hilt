package com.updavid.liveoci_hilt.core.sse.dtos

import java.time.LocalDateTime

data class CodeSSEDto(
    val id: String,
    val userId: String,
    val code: String,
    val expiresAt: LocalDateTime,
    val regeneratedAt: LocalDateTime?
)
