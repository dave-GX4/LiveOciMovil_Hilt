package com.updavid.liveoci_hilt.features.leisure.domain.usecases

import com.updavid.liveoci_hilt.features.bored.domain.entity.BoredActivity
import com.updavid.liveoci_hilt.features.leisure.domain.entity.GeminiMessage
import com.updavid.liveoci_hilt.features.leisure.domain.repository.GeminiRepository
import javax.inject.Inject

class GenerateActivityUseCase @Inject constructor(
    private val repository: GeminiRepository
) {
    suspend operator fun invoke(activity: BoredActivity): Result<GeminiMessage>{
        return try {
            Result.success(repository.generateActivityTemplate(activity))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}