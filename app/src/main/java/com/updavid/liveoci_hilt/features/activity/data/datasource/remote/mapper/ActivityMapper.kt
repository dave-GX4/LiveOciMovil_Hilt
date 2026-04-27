package com.updavid.liveoci_hilt.features.activity.data.datasource.remote.mapper

import com.updavid.liveoci_hilt.core.database.entities.ActivityEntity
import com.updavid.liveoci_hilt.core.database.entities.LeisureRecordEntity
import com.updavid.liveoci_hilt.core.database.relations.LeisureWithActivityRelation
import com.updavid.liveoci_hilt.features.activity.data.datasource.remote.models.response.LeisureWithActivityDto
import com.updavid.liveoci_hilt.features.activity.data.datasource.remote.models.response.MessageActivityResponseDto
import com.updavid.liveoci_hilt.features.activity.domain.entity.Activity
import com.updavid.liveoci_hilt.features.activity.domain.entity.ActivityMessage
import com.updavid.liveoci_hilt.features.activity.domain.entity.LeisureRecord
import java.time.format.DateTimeFormatter

fun LeisureWithActivityDto.toActivityEntity(): ActivityEntity = ActivityEntity(
    uuid = this.activityUuid,
    name = this.activityName,
    imageUrl = this.activityImageUrl,
    description = this.activityDescription,
    type = this.activityType,
    category = this.activityCategory,
    durationMinutes = this.activityEstimatedDuration,
    socialType = this.socialType
)

fun LeisureWithActivityDto.toLeisureEntity(): LeisureRecordEntity = LeisureRecordEntity(
    uuid = this.leisureUuid,
    uuidActivity = this.activityUuid,
    scheduleDate = this.scheduleDate,
    startTime = this.startTime,
    endTime = this.endTime,
    durationMinutes = this.durationMinutes,
    satisfaction = this.satisfaction,
    status = this.status,
    createdAt = this.createdAt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
)

fun LeisureWithActivityRelation.toDomain(): LeisureRecord {

    return LeisureRecord(
        id = this.leisure.uuid,
        scheduleDate = this.leisure.scheduleDate,
        startTime = this.leisure.startTime,
        endTime = this.leisure.endTime,
        durationMinutes = this.leisure.durationMinutes,
        satisfaction = this.leisure.satisfaction,
        status = this.leisure.status,
        createdAt = this.leisure.createdAt,
        activity = Activity(
            id = this.activity.uuid,
            name = this.activity.name,
            imageUrl = this.activity.imageUrl,
            description = this.activity.description,
            type = this.activity.type,
            category = this.activity.category,
            durationMinutes = this.activity.durationMinutes,
            socialType = this.activity.socialType
        )
    )
}

fun MessageActivityResponseDto.toDomain(): ActivityMessage{
    return ActivityMessage(
        message = this.message,
        status = this.status
    )
}