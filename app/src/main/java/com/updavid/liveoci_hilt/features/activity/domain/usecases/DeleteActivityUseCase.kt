package com.updavid.liveoci_hilt.features.activity.domain.usecases

import com.updavid.liveoci_hilt.features.activity.domain.entity.ActivityMessage
import com.updavid.liveoci_hilt.features.activity.domain.repository.ActivityRepository
import javax.inject.Inject

class DeleteActivityUseCase @Inject constructor(
    private val repository: ActivityRepository
) {
    suspend operator fun invoke(id: String): Result<ActivityMessage>{
        val response = repository.deleteActivity(id)

        return Result.success(response)
    }
}