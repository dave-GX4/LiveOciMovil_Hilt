package com.updavid.liveoci_hilt.features.friends.domain.usecases.request

import com.updavid.liveoci_hilt.features.friends.domain.entity.MessageFriend
import com.updavid.liveoci_hilt.features.friends.domain.repository.FriendRequestRepository
import javax.inject.Inject

class ResponseFriendRequestUseCase @Inject constructor(
    private val repository: FriendRequestRepository
) {
    suspend operator fun invoke(requestId: String, status: String): MessageFriend {
        if (requestId.isBlank()) throw Exception("El ID de la solicitud es inválido")
        if (status != "accepted" && status != "rejected") {
            throw Exception("El estado debe ser 'accepted' o 'rejected'")
        }
        return repository.responseFriendRequest(requestId, status)
    }
}