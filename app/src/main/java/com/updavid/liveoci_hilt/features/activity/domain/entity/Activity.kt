package com.updavid.liveoci_hilt.features.activity.domain.entity

data class Activity(
    val id: String,
    val name: String,
    val description: String,
    val type: String,
    val category: String,
    val durationMinutes: Int,
    val socialType: String
)
