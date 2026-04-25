package com.updavid.liveoci_hilt.features.friends.data.datasource.remote.models.response

import java.time.LocalDateTime

data class FriendRequestResponseDto(
    val id: String,
    val requesterId: String,
    val requesterName: String,
    val requesterAvatarUrl: String,
    val status: String,
    val createdAt: LocalDateTime,
)