package com.updavid.liveoci_hilt.features.schedule.domain.usecases

import com.updavid.liveoci_hilt.features.schedule.domain.entity.ScheduleMessage
import com.updavid.liveoci_hilt.features.schedule.domain.repository.ScheduleRepository
import javax.inject.Inject
import kotlin.String

class AddScheduleUseCase @Inject constructor(
    private val repository: ScheduleRepository
){
    suspend operator fun invoke(
        title: String,
        days: List<Int>,
        startTime: String,
        endTime: String,
        active: Boolean,
        type: String
    ): Result<ScheduleMessage>{
        return try {
            val response = repository.addSchedule(
                title,
                days,
                startTime,
                endTime,
                active,
                type
            )

            Result.success(response)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}