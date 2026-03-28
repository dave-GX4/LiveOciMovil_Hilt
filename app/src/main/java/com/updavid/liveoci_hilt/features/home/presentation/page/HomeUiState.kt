package com.updavid.liveoci_hilt.features.home.presentation.page

import com.updavid.liveoci_hilt.features.bored.domain.entity.BoredActivity
import com.updavid.liveoci_hilt.features.user.domain.entity.User

data class HomeUiState(
    val isLoading: Boolean = false,
    val isError: String? = null,
    val recommendedActivity: BoredActivity? = null,
    val userName: String? = null,
    val userPhotoUrl: String? = null,
    val greeting: String = ""
)
