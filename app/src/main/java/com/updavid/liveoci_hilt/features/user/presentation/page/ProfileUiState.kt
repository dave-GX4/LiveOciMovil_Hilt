package com.updavid.liveoci_hilt.features.user.presentation.page

import com.updavid.liveoci_hilt.features.user.domain.entity.User

data class ProfileUiState(
    val isError: String? = null,
    val isSuccess: String? = null,
    val user: User? = null,
    val isLoading: Boolean = false,
    val isRefresh: Boolean = false,
    val isUpdate: Boolean = false,
    val isDelete: Boolean = false,
    val notificationStatus: Boolean = true
)