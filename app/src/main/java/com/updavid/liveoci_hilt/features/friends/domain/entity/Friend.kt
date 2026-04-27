package com.updavid.liveoci_hilt.features.friends.domain.entity

import java.time.LocalDateTime

data class Friend(
    val friendshipId: String,
    val userId: String,
    val name: String,
    val avatarUrl: String?,
    val friendsSince: LocalDateTime
)