package com.updavid.liveoci_hilt.features.friends.domain.entity

import java.time.LocalDateTime

data class FriendRequest(
    val id: String,
    val requesterId: String,
    val requesterName: String,
    val requesterAvatarUrl: String?,
    val status: String,
    val createdAt: LocalDateTime
)
