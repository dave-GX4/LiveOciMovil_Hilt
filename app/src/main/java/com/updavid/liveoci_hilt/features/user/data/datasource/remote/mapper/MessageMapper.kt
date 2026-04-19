package com.updavid.liveoci_hilt.features.user.data.datasource.remote.mapper

import com.updavid.liveoci_hilt.features.user.data.datasource.remote.models.response.MessageResponseDto
import com.updavid.liveoci_hilt.features.user.domain.entity.Message

fun MessageResponseDto.toDomain(): Message{
    return Message(
        message = this.message,
        status = this.status
    )
}
