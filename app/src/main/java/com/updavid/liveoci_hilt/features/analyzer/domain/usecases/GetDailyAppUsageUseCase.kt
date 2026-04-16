package com.updavid.liveoci_hilt.features.analyzer.domain.usecases

import com.updavid.liveoci_hilt.core.system.domain.entity.AppUsageInfo
import com.updavid.liveoci_hilt.core.system.domain.service.AppDictionaryProvider
import com.updavid.liveoci_hilt.core.system.domain.service.AppUsageTracker
import javax.inject.Inject

class GetDailyAppUsageUseCase @Inject constructor(
    private val appUsageTracker: AppUsageTracker,
    private val dictionaryProvider: AppDictionaryProvider
) {
    suspend operator fun invoke(): Result<List<AppUsageInfo>> {
        return try {
            if (!appUsageTracker.hasUsagePermission()) {
                return Result.failure(Exception("Permission denied"))
            }

            val dictionaryResult = dictionaryProvider.getDictionary()
            if (dictionaryResult.isFailure) {
                return Result.failure(dictionaryResult.exceptionOrNull() ?: Exception("Error desconocido"))
            }

            val dictionary = dictionaryResult.getOrThrow()

            val rawStats = appUsageTracker.getUsageStatsForToday()

            val filteredAndCategorizedApps = rawStats.mapNotNull { rawApp ->
                val dictInfo = dictionary[rawApp.packageName]

                if (dictInfo != null && dictInfo.isLeisure) {
                    AppUsageInfo(
                        packageName = rawApp.packageName,
                        appName = rawApp.appName,
                        timeSpentMillis = rawApp.timeSpentMillis,
                        categoryName = dictInfo.categoryName
                    )
                } else {
                    null
                }
            }

            Result.success(filteredAndCategorizedApps)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}