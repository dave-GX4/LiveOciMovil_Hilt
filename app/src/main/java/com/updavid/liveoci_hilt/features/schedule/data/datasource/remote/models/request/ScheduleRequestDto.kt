package com.updavid.liveoci_hilt.features.schedule.data.datasource.remote.models.request

data class ScheduleRequestDto(
    val title: String,
    val days: List<Int>,
    val start_time: String,
    val end_time: String,
    val active: Boolean,
    val type: String
)
