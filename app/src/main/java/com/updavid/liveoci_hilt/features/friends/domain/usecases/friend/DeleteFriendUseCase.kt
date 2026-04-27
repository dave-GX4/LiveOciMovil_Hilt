package com.updavid.liveoci_hilt.features.friends.domain.usecases.friend

import com.updavid.liveoci_hilt.features.friends.domain.entity.MessageFriend
import com.updavid.liveoci_hilt.features.friends.domain.repository.FriendRepository
import javax.inject.Inject

class DeleteFriendUseCase @Inject constructor(
    private val repository: FriendRepository
) {
    suspend operator fun invoke(friendshipId: String): MessageFriend {
        if (friendshipId.isBlank()) throw Exception("ID de amistad inválido")
        return repository.removeFriend(friendshipId)
    }
}