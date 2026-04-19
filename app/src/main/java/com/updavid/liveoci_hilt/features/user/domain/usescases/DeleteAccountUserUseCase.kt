package com.updavid.liveoci_hilt.features.user.domain.usescases

import com.updavid.liveoci_hilt.features.user.domain.entity.Message
import com.updavid.liveoci_hilt.features.user.domain.repository.UserRepository
import javax.inject.Inject

class DeleteAccountUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(): Result<Message> {
        return runCatching {
            repository.deleteAccountUser()
        }
    }
}