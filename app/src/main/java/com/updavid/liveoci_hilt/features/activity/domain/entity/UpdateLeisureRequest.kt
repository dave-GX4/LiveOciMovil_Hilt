package com.updavid.liveoci_hilt.features.activity.domain.entity

data class UpdateLeisureRequest(
    val scheduleDate: String?,
    val startTime: String,
    val endTime: String,
    val satisfaction: Int,
    val status: String = "completado"
)