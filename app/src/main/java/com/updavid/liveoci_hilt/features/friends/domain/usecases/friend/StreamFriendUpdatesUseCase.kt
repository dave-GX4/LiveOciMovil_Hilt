package com.updavid.liveoci_hilt.features.friends.domain.usecases.friend

import com.updavid.liveoci_hilt.features.friends.domain.entity.FriendUpdateEvent
import com.updavid.liveoci_hilt.features.friends.domain.repository.FriendRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StreamFriendUpdatesUseCase @Inject constructor(
    private val repository: FriendRepository
) {
    operator fun invoke(): Flow<FriendUpdateEvent> {
        return repository.streamFriendUpdates()
    }
}