package com.updavid.liveoci_hilt.features.friends.data.datasource.remote.mapper

import com.updavid.liveoci_hilt.core.sse.dtos.FriendListUpdateEventDto
import com.updavid.liveoci_hilt.features.friends.data.datasource.remote.models.response.FriendRequestResponseDto
import com.updavid.liveoci_hilt.features.friends.data.datasource.remote.models.response.FriendsResponseDto
import com.updavid.liveoci_hilt.features.friends.domain.entity.Friend
import com.updavid.liveoci_hilt.features.friends.domain.entity.FriendRequest
import com.updavid.liveoci_hilt.features.friends.domain.entity.FriendUpdateEvent
import java.time.LocalDateTime

fun FriendRequestResponseDto.toDomain(): FriendRequest {
    return FriendRequest(
        id = this.id,
        requesterId = this.requesterId,
        requesterName = this.requesterName,
        requesterAvatarUrl = this.requesterAvatarUrl,
        status = this.status,
        createdAt = this.createdAt
    )
}

fun FriendsResponseDto.toDomain(): Friend{
    return Friend(
        friendshipId = this.friendshipId,
        userId = this.userId,
        name = this.name,
        avatarUrl = this.avatarUrl,
        friendsSince = this.friendsSince
    )
}

fun FriendListUpdateEventDto.toDomain(): FriendUpdateEvent {
    return FriendUpdateEvent(
        action = this.action,
        friendshipId = this.friendshipId,
        friend = if (this.action == "ADDED" && this.friendId != null && this.friendName != null) {
            Friend(
                friendshipId = this.friendshipId,
                userId = this.friendId,
                name = this.friendName,
                avatarUrl = this.friendAvatarUrl,
                friendsSince = LocalDateTime.now()
            )
        } else {
            null
        }
    )
}