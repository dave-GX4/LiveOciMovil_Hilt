package com.updavid.liveoci_hilt.features.profile.data.datasource.remote.mapper

import com.updavid.liveoci_hilt.core.database.entities.UserEntity
import com.updavid.liveoci_hilt.features.profile.data.datasource.remote.models.response.MessageResponseDto
import com.updavid.liveoci_hilt.features.profile.data.datasource.remote.models.response.UserResponseDto
import com.updavid.liveoci_hilt.features.profile.domain.entity.UserMessage
import kotlin.String

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
    password,
    notification,
    interests,
    topics,
    description,
    leisure_type
)