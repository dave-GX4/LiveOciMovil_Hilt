package com.updavid.liveoci_hilt.features.activity.domain.repository

import com.updavid.liveoci_hilt.features.activity.domain.entity.Activity
import com.updavid.liveoci_hilt.features.activity.domain.entity.ActivityMessage
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
    suspend fun getAllActivitiesRemote(userId: String)
    //suspend fun addLeisureRecord(): LeisureRecord
    //suspend fun deleteLeisureRecord(id: String): LeisureRecord
    //suspend fun getALLLeisureRecordByUser()
    fun getAllActivitiesRoom(): Flow<List<Activity>>
    fun getActivityRoom(): Flow<Activity>
}