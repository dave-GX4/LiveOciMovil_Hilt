package com.updavid.liveoci_hilt.features.profile.domain.usescases

import com.updavid.liveoci_hilt.features.profile.domain.entity.User
import com.updavid.liveoci_hilt.features.profile.domain.repository.UserRepository
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