package com.updavid.liveoci_hilt.features.friends.domain.usecases.request

data class FriendRequestUseCases(
    val getFriendRequest: GetFriendRequestUseCase,
    val sendFriendRequest: SendFriendRequestUseCase,
    val cancelFriendRequest: CancelFriendRequestUseCase,
    val responseFriendRequest: ResponseFriendRequestUseCase
)
