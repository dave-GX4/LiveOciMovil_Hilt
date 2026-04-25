package com.updavid.liveoci_hilt.features.friends.data.datasource.remote.mapper

import com.updavid.liveoci_hilt.features.friends.data.datasource.remote.models.response.MessageFriendResponseDto
import com.updavid.liveoci_hilt.features.friends.domain.entity.MessageFriend

fun MessageFriendResponseDto.toDomain(): MessageFriend{
    return MessageFriend(
        message = this.message,
        success = this.success
    )
}