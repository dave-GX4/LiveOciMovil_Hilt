package com.updavid.liveoci_hilt.features.activity.data.datasource.remote.models.response

import java.util.Date

data class LeisureRecordResposeDto(
    val uuid: String,
    val scheduleDate: Date,
    val startTime: String,
    val endTime: String,
    val durationMinutes: Number,
    val satisfaction: Number,
    val status: String
)
