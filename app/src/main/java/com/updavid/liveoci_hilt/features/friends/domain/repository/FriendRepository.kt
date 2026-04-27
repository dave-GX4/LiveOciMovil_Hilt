package com.updavid.liveoci_hilt.features.friends.domain.repository

import com.updavid.liveoci_hilt.core.sse.dtos.FriendListUpdateEventDto
import com.updavid.liveoci_hilt.features.friends.domain.entity.Friend
import com.updavid.liveoci_hilt.features.friends.domain.entity.FriendRequest
import com.updavid.liveoci_hilt.features.friends.domain.entity.FriendUpdateEvent
import com.updavid.liveoci_hilt.features.friends.domain.entity.MessageFriend
import kotlinx.coroutines.flow.Flow

interface FriendRepository {
    suspend fun cancelFriendRequest(id: String): MessageFriend
    suspend fun responseFriendRequest(id: String, status: String): MessageFriend
    suspend fun sendFriendRequest(userIdB: String): MessageFriend
    suspend fun getFriendRequest(): List<FriendRequest>
    suspend fun getAllFriends(): List<Friend>
    suspend fun removeFriend(friendshipId: String): MessageFriend
    fun streamFriendUpdates(): Flow<FriendUpdateEvent>
}