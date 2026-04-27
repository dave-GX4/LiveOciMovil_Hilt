package com.updavid.liveoci_hilt.features.home.data.datasource.remote.mapper

import com.updavid.liveoci_hilt.features.home.domain.entity.HomeNotification
import com.updavid.liveoci_hilt.features.home.domain.entity.HomeNotificationSource


fun HomeNotificationDto.toDomain(): HomeNotification {
    return HomeNotification(
        id = id,
        userId = userId,
        type = type,
        title = title,
        body = body,
        data = data ?: emptyMap(),
        isRead = read ?: isRead ?: false,
        channel = channel,
        createdAt = createdAt,
        source = HomeNotificationSource.HTTP
    )
}