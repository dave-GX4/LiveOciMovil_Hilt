package com.updavid.liveoci_hilt.features.friends.domain.usecases.friend

import com.updavid.liveoci_hilt.features.friends.domain.entity.Friend
import com.updavid.liveoci_hilt.features.friends.domain.repository.FriendRepository
import javax.inject.Inject

class GetAllFriendsUseCase @Inject constructor(
    private val repository: FriendRepository
) {
    suspend operator fun invoke(): List<Friend> {
        return repository.getAllFriends()
    }
}