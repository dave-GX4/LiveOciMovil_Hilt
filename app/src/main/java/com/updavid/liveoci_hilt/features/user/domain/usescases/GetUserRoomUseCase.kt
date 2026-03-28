package com.updavid.liveoci_hilt.features.user.domain.usescases

import com.updavid.liveoci_hilt.features.user.domain.entity.User
import com.updavid.liveoci_hilt.features.user.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserRoomUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(): Flow<User?>{
        val response = repository.getUserRoom()

        return response
    }
}