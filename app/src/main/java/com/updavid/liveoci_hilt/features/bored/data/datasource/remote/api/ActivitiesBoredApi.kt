package com.updavid.liveoci_hilt.features.bored.data.datasource.remote.api

import com.updavid.liveoci_hilt.features.bored.data.datasource.remote.models.Response.BoredActivityResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ActivitiesBoredApi {
    @GET("external/random")
    suspend fun getRandomActivity(): BoredActivityResponseDto
    @GET("external/filter")
    suspend fun getFilterActivities(
        @Query("type") type: String? = null,
        @Query("participants") participants: Int? = null
    ): List<BoredActivityResponseDto>
    @GET("external/{key}")
    suspend fun getActivityByKey(
        @Path("key") key: String
    ): BoredActivityResponseDto
}