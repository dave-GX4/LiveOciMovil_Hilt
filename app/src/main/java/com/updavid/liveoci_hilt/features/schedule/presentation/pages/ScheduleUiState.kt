package com.updavid.liveoci_hilt.features.schedule.presentation.pages

import com.updavid.liveoci_hilt.core.ui.states.StatusFilter
import com.updavid.liveoci_hilt.features.schedule.domain.entity.Schedule

data class ScheduleUiState(
    val primarySchedules: List<Schedule> = emptyList(),
    val specialSchedules: List<Schedule> = emptyList(),
    val selectedDay: Int? = null,
    val statusFilter: StatusFilter = StatusFilter.ALL,
    val isSuccess: String? = null,
    val isError: String? = null,
    val editingScheduleId: String? = null,
    val isRefreshing: Boolean = false,
    val isLoading: Boolean = false
)