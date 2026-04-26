package com.updavid.liveoci_hilt.features.friends.domain.usecases.request

import com.updavid.liveoci_hilt.features.friends.domain.entity.MessageFriend
import com.updavid.liveoci_hilt.features.friends.domain.repository.FriendRepository
import javax.inject.Inject

class CancelFriendRequestUseCase @Inject constructor(
    private val repository: FriendRepository
) {
    suspend operator fun invoke(requestId: String): MessageFriend {
        if (requestId.isBlank()) throw Exception("El ID de la solicitud es inválido")
        return repository.cancelFriendRequest(requestId)
    }
}