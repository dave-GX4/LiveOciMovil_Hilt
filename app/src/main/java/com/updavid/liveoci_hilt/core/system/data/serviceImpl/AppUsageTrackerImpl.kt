package com.updavid.liveoci_hilt.core.system.data.serviceImpl

import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.pm.PackageManager
import com.updavid.liveoci_hilt.core.system.domain.entity.AppUsageInfo
import com.updavid.liveoci_hilt.core.system.domain.entity.SystemAppUsage
import com.updavid.liveoci_hilt.core.system.domain.service.AppUsageTracker
import java.util.Calendar
import javax.inject.Inject

class AppUsageTrackerImpl @Inject constructor(
    private val context: Context
) : AppUsageTracker {
    override suspend fun getUsageStatsForToday(): List<SystemAppUsage> {
        val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val packageManager = context.packageManager

        // Definimos el rango de tiempo (Hoy desde las 00:00 hasta ahora)
        val calendar = Calendar.getInstance()
        val endTime = calendar.timeInMillis
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        val startTime = calendar.timeInMillis

        // Consultamos al sistema
        val usageStatsList = usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY,
            startTime,
            endTime
        )

        return usageStatsList
            .filter { it.totalTimeInForeground > 0 }
            .map { usageStats ->
                val appName = try {
                    val appInfo = packageManager.getApplicationInfo(usageStats.packageName, 0)
                    packageManager.getApplicationLabel(appInfo).toString()
                } catch (e: PackageManager.NameNotFoundException) {
                    usageStats.packageName
                }

                // Devolvemos el modelo crudo, que no pide categoría
                SystemAppUsage(
                    packageName = usageStats.packageName,
                    appName = appName,
                    timeSpentMillis = usageStats.totalTimeInForeground
                )
            }
            .sortedByDescending { it.timeSpentMillis }
    }

    override fun hasUsagePermission(): Boolean {
        val appOps = context.getSystemService(Context.APP_OPS_SERVICE) as android.app.AppOpsManager
        val mode = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            appOps.unsafeCheckOpNoThrow(
                android.app.AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(),
                context.packageName
            )
        } else {
            appOps.checkOpNoThrow(
                android.app.AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(),
                context.packageName
            )
        }
        return mode == android.app.AppOpsManager.MODE_ALLOWED
    }
}