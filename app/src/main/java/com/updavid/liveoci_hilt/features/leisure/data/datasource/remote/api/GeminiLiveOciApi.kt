package com.updavid.liveoci_hilt.features.leisure.data.datasource.remote.api

import com.updavid.liveoci_hilt.features.leisure.data.datasource.remote.models.request.ActivityTemplateRequestDto
import com.updavid.liveoci_hilt.features.leisure.data.datasource.remote.models.response.ActivityIaResponseDto
import com.updavid.liveoci_hilt.features.leisure.data.datasource.remote.models.response.GeminiMessageDto
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface GeminiLiveOciApi {
    @POST("/generate/option")
    suspend fun generateActivities(): List<ActivityIaResponseDto>

    @POST("/generate/{id}")
    suspend fun generateActivity(
        @Path("id") id: String,
        @Body request: ActivityTemplateRequestDto
    ): GeminiMessageDto
}