package com.updavid.liveoci_hilt.core.system.domain.service

import com.updavid.liveoci_hilt.core.system.domain.entity.AppUsageInfo

interface AppUsageTracker {
    suspend fun getUsageStatsForToday(): List<AppUsageInfo>
    fun hasUsagePermission(): Boolean
}