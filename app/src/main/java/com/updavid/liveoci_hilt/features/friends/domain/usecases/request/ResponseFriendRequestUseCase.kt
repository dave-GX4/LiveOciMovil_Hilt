package com.updavid.liveoci_hilt.features.friends.domain.usecases.request

import com.updavid.liveoci_hilt.features.friends.domain.repository.FriendRequestRepository
import javax.inject.Inject

class ResponseFriendRequestUseCase @Inject constructor(
    private val repository: FriendRequestRepository
) {
}