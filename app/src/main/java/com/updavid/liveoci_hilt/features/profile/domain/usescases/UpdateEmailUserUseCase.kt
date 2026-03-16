package com.updavid.liveoci_hilt.features.profile.domain.usescases

import com.updavid.liveoci_hilt.features.profile.domain.entity.UserMessage
import com.updavid.liveoci_hilt.features.profile.domain.repository.UserRepository
import javax.inject.Inject

class UpdateEmailUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(id: String, email: String): Result<UserMessage>{
        return try {
            val  response = repository.updateEmailUser(id, email)

            Result.success(response)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}