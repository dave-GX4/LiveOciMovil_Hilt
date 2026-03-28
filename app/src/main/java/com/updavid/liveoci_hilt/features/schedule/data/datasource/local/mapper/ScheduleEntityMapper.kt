package com.updavid.liveoci_hilt.features.schedule.data.datasource.local.mapper

import com.updavid.liveoci_hilt.core.database.entities.ScheduleEntity
import com.updavid.liveoci_hilt.features.schedule.domain.entity.Schedule

fun ScheduleEntity.toDomain() = Schedule(
    id,
    title,
    days,
    start_time,
    end_time,
    active,
    type
)