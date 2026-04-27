package com.updavid.liveoci_hilt.core.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "activities")
data class ActivityEntity(
    @PrimaryKey val uuid: String,
    val name: String,
    val imageUrl: String,
    val description: String,
    val type: String,
    val category: String,
    val durationMinutes: Int,
    val socialType: String
)