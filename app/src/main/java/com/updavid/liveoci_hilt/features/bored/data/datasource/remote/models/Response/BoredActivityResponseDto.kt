package com.updavid.liveoci_hilt.features.bored.data.datasource.remote.models.Response

data class BoredActivityResponseDto(
    val activity: String,
    val type: String,
    val  participants: Int,
    val accessibility: String,
    val duration: String,
    val kidFriendly: Boolean,
    val link: String,
    val key: String
)
