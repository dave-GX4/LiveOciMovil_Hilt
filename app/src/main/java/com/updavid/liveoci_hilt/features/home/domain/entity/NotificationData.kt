package com.updavid.liveoci_hilt.features.home.domain.entity

data class NotificationData(
    val friendshipId: String?,
    val friendId: String?,
    val friendName: String?,
    val friendAvatarUrl: String?,
    val requestId: String?,
    val requesterId: String?,
    val requesterName: String?,
    val requesterAvatarUrl: String?
)
