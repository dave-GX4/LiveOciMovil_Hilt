package com.updavid.liveoci_hilt.features.bored.data.datasource.remote.mapper

import com.updavid.liveoci_hilt.features.bored.data.datasource.remote.models.Response.BoredActivityResponseDto
import com.updavid.liveoci_hilt.features.bored.domain.entity.BoredActivity

fun BoredActivityResponseDto.toDomain(): BoredActivity{
    return BoredActivity(
        activity = this.activity,
        availability = this.availability,
        type = this.type,
        participants = this.participants,
        price = this.price,
        accessibility = this.accessibility,
        duration = this.duration,
        kidFriendly = this.kidFriendly,
        link = this.link,
        key = this.key
    )
}