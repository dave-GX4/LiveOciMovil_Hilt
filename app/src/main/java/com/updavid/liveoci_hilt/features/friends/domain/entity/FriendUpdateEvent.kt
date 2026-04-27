package com.updavid.liveoci_hilt.features.friends.domain.entity

data class FriendUpdateEvent(
    val action: String,
    val friendshipId: String,
    val friend: Friend?
)
