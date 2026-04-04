package com.updavid.liveoci_hilt.features.user.data.datasource.remote.mapper

import com.updavid.liveoci_hilt.core.database.entities.UserEntity
import com.updavid.liveoci_hilt.features.user.data.datasource.remote.models.response.MessageResponseDto
import com.updavid.liveoci_hilt.features.user.data.datasource.remote.models.response.UserResponseDto
import com.updavid.liveoci_hilt.features.user.domain.entity.UserMessage

fun MessageResponseDto.toDomain(): UserMessage{
    return UserMessage(
        message = this.message,
        status = this.status
    )
}

fun UserResponseDto.toEntity() = UserEntity(
    id,
    name,
    email,
    notification,
    interests,
    topics,
    description,
    leisureType
)