package com.updavid.liveoci_hilt.features.schedule.data.datasource.remote.models.response

data class ScheduleResponseDto (
    val id: String,
    val title: String,
    val days: List<Int>,
    val startTime: String,
    val endTime: String,
    val active: Boolean,
    val type: String
)