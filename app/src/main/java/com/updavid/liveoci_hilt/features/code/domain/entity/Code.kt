package com.updavid.liveoci_hilt.features.code.domain.entity

import java.time.LocalDateTime

data class Code(
    val id: String,
    val userId: String,
    val code: String,
    val expiresAt: LocalDateTime,
    val regeneratedAt: LocalDateTime?
)
