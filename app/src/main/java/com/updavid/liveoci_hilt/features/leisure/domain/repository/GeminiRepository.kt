package com.updavid.liveoci_hilt.features.leisure.domain.repository

import com.updavid.liveoci_hilt.features.bored.domain.entity.BoredActivity
import com.updavid.liveoci_hilt.features.leisure.domain.entity.GeminiActivity
import com.updavid.liveoci_hilt.features.leisure.domain.entity.GeminiMessage

interface GeminiRepository {
    suspend fun generateActivityTemplate(
        activity: BoredActivity
    ): GeminiMessage
    suspend fun generateActivitiesOptions(): List<GeminiActivity>
}