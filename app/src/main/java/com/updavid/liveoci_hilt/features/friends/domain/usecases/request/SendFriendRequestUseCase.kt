package com.updavid.liveoci_hilt.features.friends.domain.usecases.request

import com.updavid.liveoci_hilt.features.friends.domain.entity.MessageFriend
import com.updavid.liveoci_hilt.features.friends.domain.repository.FriendRequestRepository
import javax.inject.Inject

class SendFriendRequestUseCase @Inject constructor(
    private val repository: FriendRequestRepository
) {
    suspend operator fun invoke(userIdB: String): MessageFriend {
        if (userIdB.isBlank()) throw Exception("El ID del destinatario es inválido")
        return repository.sendFriendRequest(userIdB)
    }
}