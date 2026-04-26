package com.updavid.liveoci_hilt.core.sse.dtos

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class CodeSSEDto(
    val code: String,
    @SerializedName("expires_at")
    val expiresAt: LocalDateTime,
    @SerializedName("regenerated_at")
    val regeneratedAt: LocalDateTime?
)
