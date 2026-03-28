package com.updavid.liveoci_hilt.features.bored.domain.repository

import com.updavid.liveoci_hilt.features.bored.domain.entity.BoredActivity

interface BoredActivitiesRepository {
    suspend fun getRandomActivity(): BoredActivity

    suspend fun getFilterActivities(
        type: String? = null,
        participants: Int? = null
    ): List<BoredActivity>

    suspend fun getActivityByKey(key: String): BoredActivity
}