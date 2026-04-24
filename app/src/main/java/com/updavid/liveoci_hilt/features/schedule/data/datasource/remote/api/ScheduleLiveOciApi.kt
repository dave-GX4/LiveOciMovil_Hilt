package com.updavid.liveoci_hilt.features.schedule.data.datasource.remote.api

import com.updavid.liveoci_hilt.features.schedule.data.datasource.remote.models.request.ScheduleRequestDto
import com.updavid.liveoci_hilt.features.schedule.data.datasource.remote.models.request.ScheduleUpdateRequestDto
import com.updavid.liveoci_hilt.features.schedule.data.datasource.remote.models.response.MessageResponseDto
import com.updavid.liveoci_hilt.features.schedule.data.datasource.remote.models.response.ScheduleResponseDto

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ScheduleLiveOciApi {
    @POST("schedules/add/{id}")
    suspend fun addSchedule(
        @Path("id") userId: String,
        @Body request : ScheduleRequestDto
    ): MessageResponseDto

    @PATCH("schedules/update/{id}")
    suspend fun updateSchedule(
        @Path("id") id: String,
        @Body request: ScheduleUpdateRequestDto
    ): MessageResponseDto

    @GET("schedules/allSchedules/{id}")
    suspend fun getAllSchedules(
        @Path("id") userId: String,
    ): List<ScheduleResponseDto>

    @DELETE("schedules/delete/{id}")
    suspend fun deleteSchedule(
        @Path("id") scheduleId: String
    ): MessageResponseDto
}