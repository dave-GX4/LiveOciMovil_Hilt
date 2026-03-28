package com.updavid.liveoci_hilt.features.activity.data.datasource.remote.api

import com.updavid.liveoci_hilt.features.activity.data.datasource.remote.models.request.ActivityRequestDto
import com.updavid.liveoci_hilt.features.user.data.datasource.remote.models.response.MessageResponseDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ActivityLiveOciApi {
    @GET("")
    suspend fun getActivitiesRemote()

    @POST("/newActivity/{id}")
    suspend fun createActivityRemote(
        @Path("id") id: String,
        @Body request: ActivityRequestDto
    ): MessageResponseDto

    @DELETE("")
    suspend fun deleteActivityRemote()
}