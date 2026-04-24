package com.updavid.liveoci_hilt.features.user.data.datasource.remote.mapper

import com.updavid.liveoci_hilt.features.user.data.datasource.remote.models.response.PhotoResponseDto
import com.updavid.liveoci_hilt.features.user.domain.entity.Photo

fun PhotoResponseDto.toDomain(): Photo{
    return Photo(
        publicId = this.publicId,
        url = this.url
    )
}