package com.updavid.liveoci_hilt.features.activity.domain.usecases

import com.updavid.liveoci_hilt.features.activity.domain.entity.Activity
import com.updavid.liveoci_hilt.features.activity.domain.repository.ActivityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetActivitiesRoomUseCase @Inject constructor(
    private val repository: ActivityRepository
) {
    operator fun invoke(): Flow<List<Activity>>{
        val results = repository.getAllActivitiesRoom()

        return results
    }
}