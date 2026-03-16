package com.updavid.liveoci_hilt.features.schedule.data.datasource.remote.models.request

data class ScheduleUpdateRequestDto(
    val title: String? = null,
    val days: List<Int>? = null,
    val start_time: String? = null,
    val end_time: String? = null,
    val active: Boolean? = null
)
