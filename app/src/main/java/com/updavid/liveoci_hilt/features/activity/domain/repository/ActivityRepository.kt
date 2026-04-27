package com.updavid.liveoci_hilt.features.activity.domain.repository

import com.updavid.liveoci_hilt.features.activity.domain.entity.ActivityMessage
import com.updavid.liveoci_hilt.features.activity.domain.entity.LeisureRecord
import kotlinx.coroutines.flow.Flow

interface ActivityRepository {
    suspend fun createActivity(
        name: String,
        description: String,
        type: String,
        category: String,
        durationMinutes: Int,
        socialType: String
    ) : ActivityMessage
    suspend fun deleteActivity(): ActivityMessage
    suspend fun syncActivitiesFromRemote()
    fun getActivitiesStream(): Flow<List<LeisureRecord>>
}