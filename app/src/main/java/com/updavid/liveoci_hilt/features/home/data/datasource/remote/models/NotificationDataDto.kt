package com.updavid.liveoci_hilt.features.home.data.datasource.remote.models

data class NotificationDataDto(
    val friendshipId: String? = null,
    val friendId: String? = null,
    val friendName: String? = null,
    val friendAvatarUrl: String? = null,

    val requestId: String? = null,
    val requesterId: String? = null,
    val requesterName: String? = null,
    val requesterAvatarUrl: String? = null
)
