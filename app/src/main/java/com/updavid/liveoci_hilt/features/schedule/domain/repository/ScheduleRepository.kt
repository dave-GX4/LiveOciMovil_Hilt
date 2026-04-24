package com.updavid.liveoci_hilt.features.schedule.domain.repository

import com.updavid.liveoci_hilt.features.schedule.domain.entity.Schedule
import com.updavid.liveoci_hilt.features.schedule.domain.entity.ScheduleMessage
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {
    suspend fun addSchedule(
        title: String,
        days: List<Int>,
        startTime: String,
        endTime: String,
        active: Boolean,
        type: String
    ): ScheduleMessage
    suspend fun updateSchedule(
        id: String,
        title: String,
        days: List<Int>,
        startTime: String,
        endTime: String,
        active: Boolean
    ): ScheduleMessage
    suspend fun getAllSchedulesRemote()
    suspend fun deleteSchedule(id: String): ScheduleMessage
    fun getAllSchedulesRoom(): Flow<List<Schedule>>
    fun getScheduleByIdRoom(id: String): Flow<Schedule?>
    fun getScheduleByTypeRoom(type: String): Flow<Schedule?>
}