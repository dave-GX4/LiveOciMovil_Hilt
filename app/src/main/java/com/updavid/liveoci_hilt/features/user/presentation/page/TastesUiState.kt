package com.updavid.liveoci_hilt.features.user.presentation.page

import com.updavid.liveoci_hilt.features.user.domain.entity.User
import kotlinx.coroutines.flow.Flow

data class TastesUiState(
    val success: String? = null,
    val error: String? = null,
    val user: Flow<User>,
    val loading: Boolean = false,
    val edit: Boolean = false
)
