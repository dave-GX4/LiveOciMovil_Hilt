package com.updavid.liveoci_hilt.features.user.domain.usescases

import com.updavid.liveoci_hilt.features.user.domain.entity.Message
import com.updavid.liveoci_hilt.features.user.domain.repository.UserRepository
import javax.inject.Inject
import kotlin.String

class UpdateTastesUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(
        interests: List<String>,
        topics: List<String>,
        description: String): Result<Message>{
        return try {
            val  response = repository.updateTastesUser(
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