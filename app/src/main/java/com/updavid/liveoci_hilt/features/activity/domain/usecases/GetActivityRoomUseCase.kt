package com.updavid.liveoci_hilt.features.activity.domain.usecases

import com.updavid.liveoci_hilt.features.activity.domain.entity.Activity
import com.updavid.liveoci_hilt.features.activity.domain.repository.ActivityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetActivityRoomUseCase @Inject constructor(
    private val repository: ActivityRepository
) {
    operator fun invoke(): Flow<Activity> {
        val response = repository.getActivityRoom()

        return response
    }
}