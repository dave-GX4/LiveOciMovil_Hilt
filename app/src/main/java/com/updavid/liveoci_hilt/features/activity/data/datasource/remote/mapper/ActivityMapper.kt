package com.updavid.liveoci_hilt.features.activity.data.datasource.remote.mapper

import com.updavid.liveoci_hilt.features.activity.data.datasource.remote.models.response.ActivityResponseDto
import com.updavid.liveoci_hilt.features.activity.domain.entity.Activity

fun ActivityResponseDto.toDomain(): Activity{
    return Activity(
        id = this.id,
        name = this.name,
        description = this.description,
        type = this.type,
        category = this.category,
        durationMinutes = this.durationMinutes,
        socialType = this.socialType
    )
}