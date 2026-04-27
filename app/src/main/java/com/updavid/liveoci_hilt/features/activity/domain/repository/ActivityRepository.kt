package com.updavid.liveoci_hilt.features.activity.domain.repository

import com.updavid.liveoci_hilt.features.activity.domain.entity.ActivityMessage
import com.updavid.liveoci_hilt.features.activity.domain.entity.LeisureRecord
import com.updavid.liveoci_hilt.features.activity.domain.entity.UpdateLeisureRequest
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
    suspend fun deleteActivity(id: String): ActivityMessage
    suspend fun syncActivitiesFromRemote()
    fun getActivitiesStream(): Flow<List<LeisureRecord>>
    suspend fun updateLeisureRecord(id: String, request: UpdateLeisureRequest): Result<ActivityMessage>
}