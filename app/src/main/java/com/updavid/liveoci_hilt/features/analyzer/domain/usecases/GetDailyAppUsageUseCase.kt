package com.updavid.liveoci_hilt.features.analyzer.domain.usecases

import com.updavid.liveoci_hilt.core.system.domain.entity.AppUsageInfo
import com.updavid.liveoci_hilt.core.system.domain.service.AppUsageTracker
import javax.inject.Inject

class GetDailyAppUsageUseCase @Inject constructor(
    private val appUsageTracker: AppUsageTracker
) {
    suspend operator fun invoke(): Result<List<AppUsageInfo>> {
        return try {
            if (!appUsageTracker.hasUsagePermission()) {
                // Aquí podrías lanzar una excepción personalizada para decirle a tu UI
                // que muestre un botón de "Dar permiso"
                Result.failure(Exception("Permission denied"))
            } else {
                val stats = appUsageTracker.getUsageStatsForToday()
                Result.success(stats)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}