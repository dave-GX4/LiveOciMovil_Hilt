package com.updavid.liveoci_hilt.features.schedule.domain.entity

data class Schedule(
    val uuid: String,
    val title: String,
    val days: List<Int>,
    val startTime: String,
    val endTime: String,
    val active: Boolean,
    val type: String
)
