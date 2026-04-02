package com.updavid.liveoci_hilt.features.leisure.data.datasource.remote.models.request

data class ActivityTemplateRequestDto(
    val activity: String,
    val type: String,
    val participants: Int,
    val duration: String,
    val kidFriendly: Boolean
)
