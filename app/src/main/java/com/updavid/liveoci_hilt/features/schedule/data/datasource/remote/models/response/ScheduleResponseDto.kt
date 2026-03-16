package com.updavid.liveoci_hilt.features.schedule.data.datasource.remote.models.response

data class ScheduleResponseDto (
    val id: String,
    val title: String,
    val days: List<Int>,
    val start_time: String,
    val end_time: String,
    val active: Boolean,
    val type: String
)