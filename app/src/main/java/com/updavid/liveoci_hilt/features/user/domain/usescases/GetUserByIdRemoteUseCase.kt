package com.updavid.liveoci_hilt.features.user.domain.usescases

import com.updavid.liveoci_hilt.features.user.domain.repository.UserRepository
import javax.inject.Inject

class GetUserByIdRemoteUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(id: String){
        repository.getUserByIdRemote(id)
    }
}