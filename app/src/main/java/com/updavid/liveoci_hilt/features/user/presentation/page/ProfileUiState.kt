package com.updavid.liveoci_hilt.features.user.presentation.page

import com.updavid.liveoci_hilt.features.user.domain.entity.User

data class ProfileUiState(
    val isError: String? = null,
    val isSuccess: String? = null,

    val user: User? = null,
    val userPhotoUrl: String? = null,

    val notificationStatus: Boolean = true,
    val darkModeStatus: Boolean = false,

    val isLoading: Boolean = false,
    val isRefresh: Boolean = false,
)