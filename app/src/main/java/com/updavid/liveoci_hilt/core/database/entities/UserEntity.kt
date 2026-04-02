package com.updavid.liveoci_hilt.core.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String,
    val name: String,
    val email: String,
    val password: String,
    val notification: Boolean,
    val interests: List<String>,
    val topics: List<String>,
    val description: String,
    val leisureType: String
)
