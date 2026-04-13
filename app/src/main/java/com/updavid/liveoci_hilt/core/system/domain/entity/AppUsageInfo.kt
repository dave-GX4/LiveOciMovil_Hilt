package com.updavid.liveoci_hilt.core.system.domain.entity

data class AppUsageInfo(
    val packageName: String,
    val appName: String,
    val timeSpentMillis: Long
)
