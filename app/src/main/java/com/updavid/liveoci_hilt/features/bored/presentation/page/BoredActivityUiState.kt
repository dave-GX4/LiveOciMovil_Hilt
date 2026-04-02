package com.updavid.liveoci_hilt.features.bored.presentation.page

import com.updavid.liveoci_hilt.core.ui.states.FilterType
import com.updavid.liveoci_hilt.features.bored.domain.entity.BoredActivity
import com.updavid.liveoci_hilt.features.bored.domain.entity.CategoryModel

data class BoredActivityUiState(
    val isLoading: Boolean = false,
    val isError: String? = null,
    val successMessage: String? = null,
    val activeFilter: FilterType = FilterType.NONE,
    val isRefreshing: Boolean = false,
    val activities: List<BoredActivity> = emptyList(),
    val selectedCategory: CategoryModel? = null,
    val selectedParticipants: Int? = null
)