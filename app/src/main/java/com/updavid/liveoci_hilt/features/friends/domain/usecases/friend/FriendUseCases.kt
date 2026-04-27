package com.updavid.liveoci_hilt.features.friends.domain.usecases.friend

data class FriendUseCases(
    val deleteFriend: DeleteFriendUseCase,
    val getAllFriends: GetAllFriendsUseCase,
    val streamFriendUpdates: StreamFriendUpdatesUseCase
)
