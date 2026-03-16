package com.updavid.liveoci_hilt.features.profile.domain.usescases

import com.updavid.liveoci_hilt.features.profile.domain.entity.UserMessage
import com.updavid.liveoci_hilt.features.profile.domain.repository.UserRepository
import javax.inject.Inject

class UpdatePasswordUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(id: String, password: String): Result<UserMessage>{
        return try {
            val  response = repository.updatePasswordUser(id, password)

            Result.success(response)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}