package com.updavid.liveoci_hilt.features.schedule.domain.usecases

import com.updavid.liveoci_hilt.features.schedule.domain.entity.ScheduleMessage
import com.updavid.liveoci_hilt.features.schedule.domain.repository.ScheduleRepository
import javax.inject.Inject

class DeleteScheduleUseCase @Inject constructor(
    private val repository: ScheduleRepository
) {
    suspend operator fun invoke(id: String): Result<ScheduleMessage> {
        return try {
            val response = repository.deleteSchedule(id)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}