package com.updavid.liveoci_hilt.core.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "leisure_records",
    foreignKeys = [
        ForeignKey(
            entity = ActivityEntity::class,
            parentColumns = arrayOf("uuid"),
            childColumns = arrayOf("uuidActivity"),
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["uuidActivity"])]
)
data class LeisureRecordEntity(
    @PrimaryKey val uuid: String,
    val uuidActivity: String,
    val scheduleDate: String?,
    val startTime: String,
    val endTime: String,
    val durationMinutes: Int,
    val satisfaction: Int,
    val status: String,
    val createdAt: String
)
