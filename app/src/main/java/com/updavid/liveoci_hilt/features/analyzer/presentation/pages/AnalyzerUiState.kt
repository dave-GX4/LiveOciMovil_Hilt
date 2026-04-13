package com.updavid.liveoci_hilt.features.analyzer.presentation.pages

import com.updavid.liveoci_hilt.core.system.domain.entity.AppUsageInfo

data class AnalyzerUiState(
    val isLoading: Boolean = true,
    val appsUsage: List<AppUsageInfo> = emptyList(),
    val maxUsageMillis: Long = 1L,
    val errorMessage: String? = null,
    val requiresPermission: Boolean = false
)
