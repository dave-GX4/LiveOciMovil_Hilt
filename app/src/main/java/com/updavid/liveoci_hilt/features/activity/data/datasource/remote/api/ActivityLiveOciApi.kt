package com.updavid.liveoci_hilt.features.activity.data.datasource.remote.api

import com.updavid.liveoci_hilt.features.activity.data.datasource.remote.models.request.ActivityRequestDto
import com.updavid.liveoci_hilt.features.activity.data.datasource.remote.models.response.LeisureWithActivityDto
import com.updavid.liveoci_hilt.features.activity.data.datasource.remote.models.response.MessageActivityResponseDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ActivityLiveOciApi {
    @GET("LR/allLeisureRecords/{id}")
    suspend fun getLeisureWithActivitiesRemote(
        @Path("id") userId: String
    ): List<LeisureWithActivityDto>
    @POST("activities/newActivity/{id}")
    suspend fun createActivityRemote(
        @Path("id") id: String,
        @Body request: ActivityRequestDto
    ): MessageActivityResponseDto
    @DELETE("activities/killActivity/{id}")
    suspend fun deleteActivityRemote(
        @Path("id") id: String
    ): MessageActivityResponseDto
}