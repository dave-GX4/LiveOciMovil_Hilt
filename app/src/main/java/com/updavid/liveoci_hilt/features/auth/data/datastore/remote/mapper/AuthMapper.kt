package com.updavid.liveoci_hilt.features.auth.data.datastore.remote.mapper

import com.updavid.liveoci_hilt.features.auth.data.datastore.remote.models.response.AuthResponseDto
import com.updavid.liveoci_hilt.features.auth.domain.entity.AuthMessage

fun AuthResponseDto.toDomain(): AuthMessage{
    return AuthMessage(
        data = this.data,
        message =  this.message,
        status = this.status
    )
}