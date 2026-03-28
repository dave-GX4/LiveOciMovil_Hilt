package com.updavid.liveoci_hilt.features.bored.domain.usecases

import com.updavid.liveoci_hilt.features.bored.domain.entity.BoredActivity
import com.updavid.liveoci_hilt.features.bored.domain.repository.BoredActivitiesRepository
import javax.inject.Inject

class GetFilterActivitiesUseCase @Inject constructor(
    private val repository: BoredActivitiesRepository
) {
    suspend operator fun invoke(
        type: String? = null,
        participants: Int? = null
    ): Result<List<BoredActivity>> {
        return try {
            val response = repository.getFilterActivities(type = type, participants = participants)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}