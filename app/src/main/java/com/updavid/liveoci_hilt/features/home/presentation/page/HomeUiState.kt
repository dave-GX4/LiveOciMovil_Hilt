package com.updavid.liveoci_hilt.features.home.presentation.page

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.ui.graphics.vector.ImageVector
import com.updavid.liveoci_hilt.features.bored.domain.entity.BoredActivity
import com.updavid.liveoci_hilt.features.home.domain.entity.Notification

data class HomeUiState(
    val isLoading: Boolean = false,
    val isError: String? = null,

    val recommendedActivity: BoredActivity? = null,

    val userName: String? = null,
    val userPhotoUrl: String? = null,
    val greeting: String = "",
    val greetingIcon: ImageVector = Icons.Default.WbSunny,

    val notifications: List<Notification> = emptyList(),
    val isNotificationsLoading: Boolean = false
)