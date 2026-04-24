package com.updavid.liveoci_hilt.features.user.domain.usescases.user

import com.updavid.liveoci_hilt.features.user.domain.entity.Message
import com.updavid.liveoci_hilt.features.user.domain.repository.UserRepository
import javax.inject.Inject

class UpdateEmailUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(email: String): Result<Message>{
        return try {
            val  response = repository.updateEmailUser(email)

            Result.success(response)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}