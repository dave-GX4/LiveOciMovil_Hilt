package com.updavid.liveoci_hilt.features.schedule.data.datasource.remote.models.request

import com.google.gson.annotations.SerializedName

data class ScheduleRequestDto(
    val title: String,
    @SerializedName("day")
    val days: List<Int>,
    val startTime: String,
    val endTime: String,
    val active: Boolean,
    val type: String
)
