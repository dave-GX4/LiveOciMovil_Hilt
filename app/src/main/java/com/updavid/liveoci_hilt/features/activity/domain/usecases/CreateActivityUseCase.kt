package com.updavid.liveoci_hilt.features.activity.domain.usecases

import com.updavid.liveoci_hilt.features.activity.domain.entity.ActivityMessage
import com.updavid.liveoci_hilt.features.activity.domain.repository.ActivityRepository
import javax.inject.Inject

class CreateActivityUseCase @Inject constructor(
    private val repository: ActivityRepository
) {
    suspend operator fun invoke(
        name: String,
        description: String,
        type: String,
        category: String,
        durationMinutes: Int,
        socialType: String
    ): Result<ActivityMessage>{
        val response = repository.createActivity(
            name,
            description,
            type,
            category,
            durationMinutes,
            socialType
        )

        return Result.success(response)
    }
}