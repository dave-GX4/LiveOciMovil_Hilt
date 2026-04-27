package com.updavid.liveoci_hilt.features.home.data.datasource.remote.mapper

import com.updavid.liveoci_hilt.features.home.data.datasource.remote.models.NotificationDataDto
import com.updavid.liveoci_hilt.features.home.data.datasource.remote.models.NotificationMessageDto
import com.updavid.liveoci_hilt.features.home.domain.entity.Notification
import com.updavid.liveoci_hilt.features.home.domain.entity.NotificationData
import com.updavid.liveoci_hilt.features.home.domain.entity.NotificationMessage

fun NotificationResponseDto.toDomain(): Notification {
    return Notification(
        id = this.id,
        userId = this.userId,
        type = this.type,
        title = this.title,
        body = this.body,
        data = this.data?.toDomain(),
        isRead = this.isRead,
        channel = this.channel
    )
}

fun NotificationDataDto.toDomain(): NotificationData {
    return NotificationData(
        friendshipId = this.friendshipId,
        friendId = this.friendId,
        friendName = this.friendName,
        friendAvatarUrl = this.friendAvatarUrl,
        requestId = this.requestId,
        requesterId = this.requesterId,
        requesterName = this.requesterName,
        requesterAvatarUrl = this.requesterAvatarUrl
    )
}

fun NotificationMessageDto.toDomain(): NotificationMessage{
    return NotificationMessage(
        message = this.message,
        success = this.success
    )
}