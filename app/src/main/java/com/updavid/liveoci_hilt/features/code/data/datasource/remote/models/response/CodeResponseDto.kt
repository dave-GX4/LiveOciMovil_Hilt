package com.updavid.liveoci_hilt.features.code.data.datasource.remote.models.response

import java.time.LocalDateTime

data class CodeResponseDto(
    val id: String,
    val userId: String,
    val code: String,
    val expiresAt: LocalDateTime,
    val regeneratedAt: LocalDateTime?
)