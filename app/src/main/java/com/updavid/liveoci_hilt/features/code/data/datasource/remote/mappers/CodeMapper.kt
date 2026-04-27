package com.updavid.liveoci_hilt.features.code.data.datasource.remote.mappers

import com.updavid.liveoci_hilt.core.sse.dtos.CodeUpdateEventDto
import com.updavid.liveoci_hilt.features.code.data.datasource.remote.models.response.CodeResponseDto
import com.updavid.liveoci_hilt.features.code.data.datasource.remote.models.response.FoundUserResponseDto
import com.updavid.liveoci_hilt.features.code.domain.entity.Code
import com.updavid.liveoci_hilt.features.code.domain.entity.FoundUser

fun CodeUpdateEventDto.toDomain(recordId: String, currentUserId: String): Code {
    return Code(
        id = recordId,
        userId = currentUserId,
        code = this.code,
        expiresAt = this.expiresAt,
        regeneratedAt = this.regeneratedAt
    )
}

fun CodeResponseDto.toDomain(): Code {
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
        code = this.code,
        requestId = this.requestId?.toString(),
        requestStatus = this.requestStatus?.toString(),
        isRequester = this.isRequester
    )
}