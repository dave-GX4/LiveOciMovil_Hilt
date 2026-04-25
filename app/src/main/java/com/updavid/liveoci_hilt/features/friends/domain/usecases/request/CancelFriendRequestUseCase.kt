package com.updavid.liveoci_hilt.features.friends.domain.usecases.request

import com.updavid.liveoci_hilt.features.friends.domain.entity.MessageFriend
import com.updavid.liveoci_hilt.features.friends.domain.repository.FriendRequestRepository
import javax.inject.Inject

class CancelFriendRequestUseCase @Inject constructor(
    private val repository: FriendRequestRepository
) {
    suspend operator fun invoke(requestId: String): MessageFriend {
        if (requestId.isBlank()) throw Exception("El ID de la solicitud es inválido")
        return repository.cancelFriendRequest(requestId)
    }
}