package com.updavid.liveoci_hilt.features.schedule.data.datasource.remote.mappers

import com.updavid.liveoci_hilt.core.database.entities.ScheduleEntity
import com.updavid.liveoci_hilt.features.schedule.data.datasource.remote.models.response.MessageResponseDto
import com.updavid.liveoci_hilt.features.schedule.data.datasource.remote.models.response.ScheduleResponseDto
import com.updavid.liveoci_hilt.features.schedule.domain.entity.ScheduleMessage

fun MessageResponseDto.toDomain(): ScheduleMessage{
    return ScheduleMessage(
        message = this.message,
        status = this.status
    )
}

fun ScheduleResponseDto.toEntity() = ScheduleEntity(
    uuid = uuid,
    title = title,
    days = days,
    startTime = startTime,
    endTime = endTime,
    active = active,
    type = type
)