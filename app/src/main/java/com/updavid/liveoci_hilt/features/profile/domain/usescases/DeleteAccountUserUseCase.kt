package com.updavid.liveoci_hilt.features.profile.domain.usescases

import com.updavid.liveoci_hilt.features.profile.domain.entity.UserMessage
import com.updavid.liveoci_hilt.features.profile.domain.repository.UserRepository
import javax.inject.Inject

class DeleteAccountUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(id: String): Result<UserMessage>{
        val response = repository.deleteAccountUser(id)

        return Result.success(response)
    }
}