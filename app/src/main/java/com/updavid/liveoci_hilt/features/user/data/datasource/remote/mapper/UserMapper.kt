package com.updavid.liveoci_hilt.features.user.data.datasource.remote.mapper

import androidx.datastore.preferences.protobuf.LazyStringArrayList.emptyList
import com.updavid.liveoci_hilt.core.database.entities.UserEntity
import com.updavid.liveoci_hilt.features.user.data.datasource.remote.models.response.UserResponseDto

fun UserResponseDto.toEntity() = UserEntity(
    id,
    name,
    email,
    notification = notification ?: false,
    interests = interests ?: emptyList(),
    topics = topics ?: emptyList(),
    description = description?.toString() ?: "",
    leisureType = leisureType ?: "Pasivo"
)