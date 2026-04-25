package com.updavid.liveoci_hilt.features.friends.domain.repository

import com.updavid.liveoci_hilt.features.friends.domain.entity.MessageFriend

interface FriendRequestRepository {
    suspend fun cancelFriendRequest(id: String): MessageFriend
    suspend fun responseFriendRequest(id: String, status: String): MessageFriend
    suspend fun sendFriendRequest(userIdB: String): MessageFriend
    suspend fun getFriendRequest(): MessageFriend
}