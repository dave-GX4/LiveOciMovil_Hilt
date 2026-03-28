package com.updavid.liveoci_hilt.features.activity.data.datasource.remote.models.request

data class ActivityRequestDto(
    val name: String,
    val description: String,
    val type: String,
    val category: String,
    val durationMinutes: Int,
    val socialType: String
)
