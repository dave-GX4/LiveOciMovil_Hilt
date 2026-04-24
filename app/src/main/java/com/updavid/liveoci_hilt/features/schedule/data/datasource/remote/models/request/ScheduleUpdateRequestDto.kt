package com.updavid.liveoci_hilt.features.schedule.data.datasource.remote.models.request

data class ScheduleUpdateRequestDto(
    val title: String? = null,
    val days: List<Int>? = null,
    val startTime: String? = null,
    val endTime: String? = null,
    val active: Boolean? = null
)
