package com.updavid.liveoci_hilt.features.friends.data.datasource.remote.mapper

import com.updavid.liveoci_hilt.features.friends.data.datasource.remote.models.response.FriendRequestResponseDto
import com.updavid.liveoci_hilt.features.friends.domain.entity.FriendRequest

fun FriendRequestResponseDto.toDomain(): FriendRequest{
    return FriendRequest(
        id = this.id,
        requesterId = this.requesterId,
        requesterName = this.requesterName,
        requesterAvatarUrl = this.requesterAvatarUrl,
        status = this.status,
        createdAt = this.createdAt
    )
}