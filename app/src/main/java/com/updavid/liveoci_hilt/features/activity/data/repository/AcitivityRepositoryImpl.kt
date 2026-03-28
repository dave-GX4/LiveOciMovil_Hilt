package com.updavid.liveoci_hilt.features.activity.data.repository

import com.updavid.liveoci_hilt.features.activity.data.datasource.remote.api.ActivityLiveOciApi
import com.updavid.liveoci_hilt.features.activity.domain.entity.Activity
import com.updavid.liveoci_hilt.features.activity.domain.entity.ActivityMessage
import com.updavid.liveoci_hilt.features.activity.domain.repository.ActivityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ActivityRepositoryImpl @Inject constructor(
    private val api: ActivityLiveOciApi
): ActivityRepository {
    override suspend fun createActivity(
        name: String,
        description: String,
        type: String,
        category: String,
        durationMinutes: Int,
        socialType: String
    ): ActivityMessage {
        TODO("Not yet implemented")
    }

    override suspend fun deleteActivity(id: String): ActivityMessage {
        TODO("Not yet implemented")
    }

    override suspend fun getAllActivitiesRemote(userId: String) {
        TODO("Not yet implemented")
    }

    override fun getAllActivitiesRoom(): Flow<List<Activity>> {
        TODO("Not yet implemented")
    }

    override fun getActivityRoom(): Flow<Activity> {
        TODO("Not yet implemented")
    }
}