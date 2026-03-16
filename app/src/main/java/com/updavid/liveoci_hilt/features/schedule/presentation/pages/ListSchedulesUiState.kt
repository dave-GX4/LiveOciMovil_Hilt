package com.updavid.liveoci_hilt.features.schedule.presentation.pages

import com.updavid.liveoci_hilt.features.schedule.domain.entity.Schedule

data class ListSchedulesUiState(
    val isLoading: Boolean = false,
    val isError: String? = null,
    val schedules: List<Schedule> = emptyList(),
    val isRefreshing: Boolean = false,
    val errorMessage: String? = null
)