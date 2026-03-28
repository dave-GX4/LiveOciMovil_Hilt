package com.updavid.liveoci_hilt.features.schedule.domain.repository

import com.updavid.liveoci_hilt.features.schedule.domain.entity.Schedule
import com.updavid.liveoci_hilt.features.schedule.domain.entity.ScheduleMessage
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {
    suspend fun addSchedule(
        title: String,
        days: List<Int>,
        start_time: String,
        end_time: String,
        active: Boolean,
        type: String
    ): ScheduleMessage
    suspend fun updateSchedule(
        id: String,
        title: String,
        days: List<Int>,
        start_time: String,
        end_time: String,
        active: Boolean
    ): ScheduleMessage
    suspend fun getAllSchedulesRemote()
    fun getAllSchedulesRoom(): Flow<List<Schedule>>
    //suspend fun getScheduleRoom(): Flow<Schedule?>
}