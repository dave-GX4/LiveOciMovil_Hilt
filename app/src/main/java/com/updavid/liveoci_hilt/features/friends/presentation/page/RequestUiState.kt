package com.updavid.liveoci_hilt.features.friends.presentation.page

import com.updavid.liveoci_hilt.features.friends.domain.entity.FriendRequest

data class RequestUiState(
    val isSuccess: String? = null,
    val isError: String? = null,
    val friendRequests: List<FriendRequest> = emptyList(),
    val isLoading: Boolean = false
)
