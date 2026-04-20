package com.updavid.liveoci_hilt.features.user.domain.usescases.user

import com.updavid.liveoci_hilt.features.user.domain.repository.UserRepository
import jakarta.inject.Inject

class LogoutUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(): Result<Unit> = repository.logoutLocal()
}