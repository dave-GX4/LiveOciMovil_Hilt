package com.updavid.liveoci_hilt.features.user.data.datasource.local

import com.updavid.liveoci_hilt.core.database.entities.UserEntity
import com.updavid.liveoci_hilt.features.user.domain.entity.User

fun UserEntity.toDomain() = User(
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