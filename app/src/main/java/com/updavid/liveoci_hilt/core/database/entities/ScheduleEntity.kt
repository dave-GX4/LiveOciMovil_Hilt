package com.updavid.liveoci_hilt.core.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.updavid.liveoci_hilt.core.database.convert.Converters

@TypeConverters(Converters::class)
@Entity(tableName = "schedules")
data class ScheduleEntity(
    @PrimaryKey val uuid: String,
    val title: String,
    val days: List<Int>,
    val startTime: String,
    val endTime: String,
    val active: Boolean,
    val type: String
)