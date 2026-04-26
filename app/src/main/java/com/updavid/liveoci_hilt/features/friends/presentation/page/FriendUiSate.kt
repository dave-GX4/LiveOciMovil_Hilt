package com.updavid.liveoci_hilt.features.friends.presentation.page

import com.updavid.liveoci_hilt.features.friends.domain.entity.Friend

data class FriendUiSate(
    val isSuccess: String? = null,
    val isError: String? = null,
    val friends: List<Friend> = emptyList(),
    val isLoading: Boolean = false
)
