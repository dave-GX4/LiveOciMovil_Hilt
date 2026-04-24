package com.updavid.liveoci_hilt.features.activity.data.datasource.remote.api

import com.updavid.liveoci_hilt.features.activity.data.datasource.remote.models.request.ActivityRequestDto
import com.updavid.liveoci_hilt.features.activity.data.datasource.remote.models.response.ActivityResponseDto
import com.updavid.liveoci_hilt.features.activity.data.datasource.remote.models.response.LeisureRecordResposeDto
import com.updavid.liveoci_hilt.features.activity.data.datasource.remote.models.response.MessageActivityResponseDto
import com.updavid.liveoci_hilt.features.activity.domain.entity.ActivityMessage
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ActivityLiveOciApi {
    @GET("activities/AllActivities/{id}")
    suspend fun getActivitiesRemote(
        @Path("id") id: String
    ): ActivityResponseDto

    @POST("activities/newActivity/{id}")
    suspend fun createActivityRemote(
        @Path("id") id: String,
        @Body request: ActivityRequestDto
    ): MessageActivityResponseDto

    @DELETE("activities/killActivity/{id}")
    suspend fun deleteActivityRemote(
        @Path("id") id: String
    ): MessageActivityResponseDto

    @GET("LR/allLeisureRecords/{id}")
    suspend fun getLeisureRecords(
        @Path("id") id: String
    ): LeisureRecordResposeDto

    @PATCH("LR/update/{id}")
    suspend fun updateReplaceActivity(
        @Path("id") id: String
    ): MessageActivityResponseDto
}