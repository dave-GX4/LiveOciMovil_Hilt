package com.updavid.liveoci_hilt.features.activity.presentation.page

import com.updavid.liveoci_hilt.features.activity.domain.entity.LeisureRecord

data class ActivitiesUiState(
    val isSuccess: String? = null,
    val isError: String? = null,
    val timeFinished: Int = 0,

    val leisureRecord: List<LeisureRecord?> = emptyList(),

    val isReset: Boolean = false,
    val isLoading: Boolean = false,
)
