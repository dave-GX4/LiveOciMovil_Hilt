package com.updavid.liveoci_hilt.features.bored.presentation.page

import com.updavid.liveoci_hilt.features.bored.domain.entity.BoredActivity
import com.updavid.liveoci_hilt.features.bored.domain.entity.CategoryModel

data class BoredActivityUiState(
    val isLoading: Boolean = false,
    val isError: String? = null,
    val isRefreshing: Boolean = false,
    val activities: List<BoredActivity> = emptyList(),
    val selectedCategory: CategoryModel? = null,
    val selectedParticipants: Int? = null
)