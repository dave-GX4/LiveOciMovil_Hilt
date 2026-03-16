package com.updavid.liveoci_hilt.features.schedule.domain.usecases

import com.updavid.liveoci_hilt.features.schedule.domain.entity.ScheduleMessage
import com.updavid.liveoci_hilt.features.schedule.domain.repository.ScheduleRepository
import javax.inject.Inject

class UpdateScheduleUseCase @Inject constructor(
    private val  repository: ScheduleRepository
) {
    suspend operator fun invoke(
        id: String,
        title: String,
        days: List<Int>,
        start_time: String,
        end_time: String,
        active: Boolean
    ): Result<ScheduleMessage>{
        return try {
            val response = repository.updateSchedule(
                id,
                title,
                days,
                start_time,
                end_time,
                active
            )

            Result.success(response)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}