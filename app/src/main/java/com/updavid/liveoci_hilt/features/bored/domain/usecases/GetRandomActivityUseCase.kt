package com.updavid.liveoci_hilt.features.bored.domain.usecases

import com.updavid.liveoci_hilt.features.bored.domain.entity.BoredActivity
import com.updavid.liveoci_hilt.features.bored.domain.repository.BoredActivitiesRepository
import javax.inject.Inject

class GetRandomActivityUseCase @Inject constructor(
    private val repository: BoredActivitiesRepository
) {
    suspend operator fun invoke(): Result<BoredActivity>{
        return try {
            val response = repository.getRandomActivity()

            Result.success(response)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}