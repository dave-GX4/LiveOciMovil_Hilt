package com.updavid.liveoci_hilt.features.activity.domain.usecases

import com.updavid.liveoci_hilt.features.activity.domain.repository.ActivityRepository
import javax.inject.Inject

class GetActivitiesRemoteUseCase @Inject constructor(
    private val repository: ActivityRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return try {
            repository.syncActivitiesFromRemote()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}