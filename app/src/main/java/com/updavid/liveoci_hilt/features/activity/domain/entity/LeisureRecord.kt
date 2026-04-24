package com.updavid.liveoci_hilt.features.activity.domain.entity

import java.util.Date

data class LeisureRecord(
    val uuid: String,
    val scheduleDate: Date,
    val startTime: String,
    val endTime: String,
    val durationMinutes: Number,
    val satisfaction: Number,
    val status: String
)
