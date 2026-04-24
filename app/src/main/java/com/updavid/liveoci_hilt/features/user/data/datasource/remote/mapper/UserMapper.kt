package com.updavid.liveoci_hilt.features.user.data.datasource.remote.mapper

import com.updavid.liveoci_hilt.core.database.entities.UserEntity
import com.updavid.liveoci_hilt.features.user.data.datasource.remote.models.response.UserResponseDto

fun UserResponseDto.toEntity(): UserEntity{
    return UserEntity(
        id = this.id,
        name = this.name,
        email = this.email,
        notification = this.notification,
        interests = this.interests ?: emptyList(),
        topics = this.topics?: emptyList(),
        description = this.description,
        leisureType = this.leisureType
    )
}