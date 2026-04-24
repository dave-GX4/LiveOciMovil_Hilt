package com.updavid.liveoci_hilt.features.code.data.datasource.remote.mappers

import com.updavid.liveoci_hilt.core.ssedatasource.dtos.CodeSSEDto
import com.updavid.liveoci_hilt.features.code.data.datasource.remote.models.response.FoundUserResponseDto
import com.updavid.liveoci_hilt.features.code.domain.entity.Code
import com.updavid.liveoci_hilt.features.code.domain.entity.FoundUser

fun CodeSSEDto.toDomain(): Code {
    return Code(
        id = this.id,
        userId = this.userId,
        code = this.code,
        expiresAt = this.expiresAt,
        regeneratedAt = this.regeneratedAt
    )
}

fun FoundUserResponseDto.toDomain(): FoundUser {
    return FoundUser(
        id = this.id,
        name = this.name,
        avatarUrl = this.avatarUrl,
        code = this.code
    )
}