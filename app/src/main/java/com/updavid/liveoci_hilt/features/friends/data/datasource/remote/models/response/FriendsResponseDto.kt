package com.updavid.liveoci_hilt.features.friends.data.datasource.remote.models.response

import java.time.LocalDateTime

data class FriendsResponseDto(
    val friendshipId: String,
    val userId: String,
    val name: String,
    val avatarUrl: String?,
    val friendsSince: LocalDateTime
)
