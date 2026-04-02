package com.updavid.liveoci_hilt.features.leisure.data.datasource.remote.mapper

import com.updavid.liveoci_hilt.features.leisure.data.datasource.remote.models.response.ActivityIaResponseDto
import com.updavid.liveoci_hilt.features.leisure.data.datasource.remote.models.response.GeminiMessageDto
import com.updavid.liveoci_hilt.features.leisure.domain.entity.GeminiActivity
import com.updavid.liveoci_hilt.features.leisure.domain.entity.GeminiMessage

fun GeminiMessageDto.toDomain(): GeminiMessage{
    return GeminiMessage(
        message = this.message,
        status = this.status
    )
}

fun ActivityIaResponseDto.toDomain(): GeminiActivity{
    return GeminiActivity(
        title = this.title,
        description = this.description,
        type = this.type,
        category = this.category,
        duration = this.duration,
        socialType = this.socialType
    )
}