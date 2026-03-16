package com.updavid.liveoci_hilt.features.schedule.data.datasource.remote.api

import com.updavid.liveoci_hilt.features.schedule.data.datasource.remote.models.request.ScheduleRequestDto
import com.updavid.liveoci_hilt.features.schedule.data.datasource.remote.models.request.ScheduleUpdateRequestDto
import com.updavid.liveoci_hilt.features.schedule.data.datasource.remote.models.response.MessageResponseDto
import com.updavid.liveoci_hilt.features.schedule.data.datasource.remote.models.response.ScheduleResponseDto

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ScheduleLiveOciApi {
    @POST("schedules/add")
    suspend fun addSchedule(
        @Body request : ScheduleRequestDto
    ): MessageResponseDto

    @PATCH("schedules/update/{id}")
    suspend fun updateSchedule(
        @Path("id") id: String,
        @Body request: ScheduleUpdateRequestDto
    ): MessageResponseDto

    @GET("schedules/")
    suspend fun getAllSchedules(): List<ScheduleResponseDto>
}