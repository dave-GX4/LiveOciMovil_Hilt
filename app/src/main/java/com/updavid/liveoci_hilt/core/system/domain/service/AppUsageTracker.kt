package com.updavid.liveoci_hilt.core.system.domain.service

import com.updavid.liveoci_hilt.core.system.domain.entity.SystemAppUsage

interface AppUsageTracker {
    suspend fun getUsageStatsForToday(): List<SystemAppUsage>
    fun hasUsagePermission(): Boolean
}