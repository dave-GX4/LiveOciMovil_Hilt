package com.updavid.liveoci_hilt.features.profile.domain.usescases

import com.updavid.liveoci_hilt.features.profile.domain.entity.UserMessage
import com.updavid.liveoci_hilt.features.profile.domain.repository.UserRepository
import javax.inject.Inject
import kotlin.String

class UpdateTastesUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(
        id: String,
        interests: List<String>,
        topics: List<String>,
        description: String): Result<UserMessage>{
        return try {
            val  response = repository.updateTastesUser(
                id,
                interests,
                topics,
                description
            )

            Result.success(response)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}