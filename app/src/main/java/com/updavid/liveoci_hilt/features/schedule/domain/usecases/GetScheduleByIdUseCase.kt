package com.updavid.liveoci_hilt.features.schedule.domain.usecases

import com.updavid.liveoci_hilt.features.schedule.domain.entity.Schedule
import com.updavid.liveoci_hilt.features.schedule.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetScheduleByIdUseCase @Inject constructor(
    private val repository: ScheduleRepository
) {
    operator fun invoke(id: String): Flow<Schedule?> {
        return repository.getScheduleByIdRoom(id)
    }
}