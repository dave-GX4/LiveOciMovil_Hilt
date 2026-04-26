package com.updavid.liveoci_hilt.features.friends.domain.usecases.request

import com.updavid.liveoci_hilt.features.friends.domain.entity.FriendRequest
import com.updavid.liveoci_hilt.features.friends.domain.repository.FriendRepository
import javax.inject.Inject

class GetFriendRequestUseCase @Inject constructor(
    private val repository: FriendRepository
) {
    suspend operator fun invoke(): List<FriendRequest> {
        return repository.getFriendRequest()
    }
}