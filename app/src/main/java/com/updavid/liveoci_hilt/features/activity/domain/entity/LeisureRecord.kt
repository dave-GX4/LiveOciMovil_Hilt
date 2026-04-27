package com.updavid.liveoci_hilt.features.activity.domain.entity

data class LeisureRecord(
    val id: String,
    val scheduleDate: String?,
    val startTime: String,
    val endTime: String,
    val durationMinutes: Int,
    val satisfaction: Int,
    val status: String,
    val activity: Activity,
    val createdAt: String
)