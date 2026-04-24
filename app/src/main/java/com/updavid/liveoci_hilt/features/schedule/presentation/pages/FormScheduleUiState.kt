package com.updavid.liveoci_hilt.features.schedule.presentation.pages

data class FormScheduleUiState(
    val title: String = "",
    val type: String = "special",
    val days: List<Int> = emptyList(),
    val startTime: String = "08:00",
    val endTime: String = "09:00",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
)