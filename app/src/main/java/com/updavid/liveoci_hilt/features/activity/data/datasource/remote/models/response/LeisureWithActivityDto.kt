package com.updavid.liveoci_hilt.features.activity.data.datasource.remote.models.response

import java.time.LocalDateTime

data class LeisureWithActivityDto(
    val leisureUuid: String,
    val scheduleDate: String?,
    val startTime: String,
    val endTime: String,
    val durationMinutes: Int,
    val satisfaction: Int,
    val status: String,
    val createdAt: LocalDateTime,

    val activityUuid: String,
    val activityName: String,
    val activityImageUrl: String,
    val activityDescription: String,
    val activityType: String,
    val activityCategory: String,
    val activityEstimatedDuration: Int,
    val socialType: String
)