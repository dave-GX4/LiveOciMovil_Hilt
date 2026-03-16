package com.updavid.liveoci_hilt.features.schedule.presentation.pages

data class FormScheduleUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isError: String? = null,

    val id: String? = null,
    val title: String = "",
    val days: List<Int> = emptyList(),
    val startTime: String = "",
    val endTime: String = "",
    val isActive: Boolean = true,
    val type: String = "",

    val titleError: String? = null,
    val daysError: String? = null,
    val timeError: String? = null
)
