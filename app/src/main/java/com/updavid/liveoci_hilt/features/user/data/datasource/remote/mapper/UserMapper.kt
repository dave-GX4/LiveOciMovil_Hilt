package com.updavid.liveoci_hilt.features.user.data.datasource.remote.mapper

import com.updavid.liveoci_hilt.core.database.entities.UserEntity
import com.updavid.liveoci_hilt.features.user.data.datasource.remote.models.response.UserResponseDto

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