package com.updavid.liveoci_hilt.features.activity.domain.usecases

import com.updavid.liveoci_hilt.features.activity.domain.entity.ActivityMessage
import com.updavid.liveoci_hilt.features.activity.domain.repository.ActivityRepository
import javax.inject.Inject

class DeleteActivityUseCase @Inject constructor(
    private val repository: ActivityRepository
) {
    suspend operator fun invoke(id: String): Result<ActivityMessage> {
        return try {
            val response = repository.deleteActivity(id)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}