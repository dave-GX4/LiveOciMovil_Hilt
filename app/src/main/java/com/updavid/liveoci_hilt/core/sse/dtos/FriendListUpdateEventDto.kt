package com.updavid.liveoci_hilt.core.sse.dtos

data class FriendListUpdateEventDto(
    val action: String,
    val friendshipId: String,
    val friendId: String?,
    val friendName: String?,
    val friendAvatarUrl: String?
)
