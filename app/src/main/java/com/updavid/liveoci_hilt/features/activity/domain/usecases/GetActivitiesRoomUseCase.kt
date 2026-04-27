package com.updavid.liveoci_hilt.features.activity.domain.usecases

import com.updavid.liveoci_hilt.features.activity.domain.entity.Activity
import com.updavid.liveoci_hilt.features.activity.domain.entity.LeisureRecord
import com.updavid.liveoci_hilt.features.activity.domain.repository.ActivityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetActivitiesRoomUseCase @Inject constructor(
    private val repository: ActivityRepository
) {
    operator fun invoke(): Flow<List<LeisureRecord>>{
        val results = repository.getActivitiesStream()

        return results
    }
}